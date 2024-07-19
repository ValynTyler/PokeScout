use std::{error::Error, sync::Arc};

use calamine::{open_workbook, Reader, Xlsx};
use rustemon::{client::RustemonClient, pokemon::pokemon_species};
use tokio::{sync::Mutex, task};

use rust_xlsxwriter::*;

type Candidate = (i64, String, i32);

#[tokio::main]
async fn main() {
    let rustemon_client: Arc<Mutex<RustemonClient>> =
        Arc::new(Mutex::new(RustemonClient::default()));

    let list = read_spreadsheet().unwrap();
    let mut data: Vec<RichData> = vec![];

    let mut j = 0;
    for item in list {
        j += 1;
        let client_clone = Arc::clone(&rustemon_client);
        let handler_lock = client_clone.lock().await;
        let handle = &*handler_lock;
        data.push(
            item.toRichData(handle).await.unwrap()
        );
    }

    write_xlsl(data);
}

fn write_xlsl(data: Vec<RichData>) -> Result<(), XlsxError> {
    let mut workbook = Workbook::new();
    let worksheet = workbook.add_worksheet();

    worksheet.write(0, 0, "Nume")?;
    worksheet.write(0, 1, "Ramură de vârstă")?;
    worksheet.write(0, 2, "Nume pokemon")?;
    worksheet.write(0, 3, "ID pokemon")?;

    let mut i = 0;
    for item in data {
        i += 1;
        worksheet.write(i, 0, item.data.name)?;
        worksheet.write(i, 1, item.data.ramura_de_varsta)?;
        worksheet.write(i, 2, item.data.pokemon)?;
        worksheet.write(i, 3, item.id)?;
    }

    workbook.save("out.xlsx")?;

    Ok(())
}

struct ListData {
    pub name: String,
    pub ramura_de_varsta: String,
    pub pokemon: String,
}

struct RichData {
    data: ListData,
    id: String,
}

impl ListData {
    async fn toRichData(self: Self, handle: &RustemonClient) -> Result<RichData, Box<dyn std::error::Error>> {
        let pokemon = self.pokemon.clone();
        Ok(RichData {
            data: self,
            id: match pokemon_species::get_by_name(&pokemon, &handle).await {
                Ok(v) => v.id.to_string(),
                Err(_) => "".to_owned(),
            },
        })
    }
}

fn read_spreadsheet() -> Result<Vec<ListData>, Box<dyn std::error::Error>> {
    // Open the workbook
    let mut workbook: Xlsx<_> = open_workbook("raw.xlsx")?;
    let mut data: Vec<ListData> = vec![];

    // Select a worksheet
    if let Some(Ok(range)) = workbook.worksheet_range("Form Responses 1") {
        for row in range.rows() {
            data.push(ListData {
                name: match row.get(1) {
                    Some(v) => v.to_string(),
                    None => "".to_owned(),
                },
                ramura_de_varsta: match row.get(2) {
                    Some(v) => v.to_string(),
                    None => "".to_owned(),
                },
                pokemon: match row.get(3) {
                    Some(v) => v.to_string()
                        .replace("-evoluții-1", "")
                        .replace("-evoluții-2", "")
                        .replace("-evoluții-3", ""),
                    None => "".to_owned(),
                }
            })
        }
    } else {
        println!("Cannot find 'Sheet1'");
    }

    Ok(data)
}
