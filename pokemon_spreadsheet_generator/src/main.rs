use core::panic;
use std::sync::Arc;

use rustemon::{client::RustemonClient, model::pokemon::PokemonSpecies, pokemon::pokemon_species};
use tokio::{sync::Mutex, task};

use rust_xlsxwriter::*;

#[tokio::main]
async fn main() {
    let rustemon_client: Arc<Mutex<RustemonClient>> =
        Arc::new(Mutex::new(RustemonClient::default()));

    let mut pokemon = vec![];

    for item in get_all_species(&rustemon_client).await {
        if !item.is_legendary && !item.is_mythical && item.evolves_from_species == None {
            pokemon.push((item.id, item.name));
        }
    }

    println!("Selected Pokemon: {:?}", pokemon);
    write_xlsl(&pokemon).unwrap();
}

fn write_xlsl(pokemon: &Vec<(i64, String)>) -> Result<(), XlsxError> {
    let mut workbook = Workbook::new();
    let worksheet = workbook.add_worksheet();

    worksheet.write(0, 0, "Index")?;
    worksheet.write(0, 1, "ID")?;
    worksheet.write(0, 2, "Pokemon")?;

    let mut i = 1;
    for item in pokemon {
        worksheet.write(i, 0, i)?;
        worksheet.write(i, 1, item.0)?;
        worksheet.write(i, 2, item.1.clone())?;
        i += 1;
    }

    workbook.save("demo.xlsx")?;
    Ok(())
}

async fn get_all_species(client: &Arc<Mutex<RustemonClient>>) -> Vec<PokemonSpecies> {
    let mut species = vec![];
    let mut tasks = vec![];

    for i in 1..1026 {
        let client_clone = Arc::clone(&client);

        let task = task::spawn(async move {
            let handler_lock = client_clone.lock().await;
            let handle = &*handler_lock;

            pokemon_species::get_by_id(i, &handle).await
        });

        tasks.push(task);
    }

    for task in tasks.drain(..) {
        match task.await.unwrap() {
            Ok(pokemon) => {
                species.push(pokemon);
            }
            Err(e) => panic!("{}", e),
        }
    }

    species
}
