use core::panic;
use std::sync::Arc;

use rustemon::{client::RustemonClient, model::pokemon::PokemonSpecies, pokemon::pokemon_species, Follow};
use tokio::{sync::Mutex, task};

use rust_xlsxwriter::*;

type Candidate = (i64, String, i32);

#[tokio::main]
async fn main() {
    let rustemon_client: Arc<Mutex<RustemonClient>> =
        Arc::new(Mutex::new(RustemonClient::default()));

    let mut pokemon: Vec<Candidate> = vec![];

    let species = get_all_species(&rustemon_client).await;
    let mut i = 1;
    for item in species {
        let client_clone = Arc::clone(&rustemon_client);
        let handler_lock = client_clone.lock().await;
        let handle = &*handler_lock;

        if !item.is_legendary && !item.is_mythical && item.evolves_from_species == None {
            let evolution_chain_resource = item.evolution_chain.unwrap();
            let evolution_chain = evolution_chain_resource.follow(&handle).await.unwrap();
            
            let mut chain_link = &evolution_chain.chain;
            let mut stages = 1;

            while chain_link.evolves_to.len() != 0 {
                chain_link = &chain_link.evolves_to[0];
                stages += 1;
            }
            println!("Determining viability: {}/1025", i);    
            i += 1;
            pokemon.push((item.id, item.name, stages));
        }
    }

    for item in &pokemon {
        println!("{:?}", item);
    }

    write_xlsl(&pokemon).unwrap();
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

        println!("Loading tasks: {i}/1025");
        tasks.push(task);
    }

    let mut i = 0;
    for task in tasks.drain(..) {
        match task.await.unwrap() {
            Ok(pokemon) => {
                species.push(pokemon);
            }
            Err(e) => panic!("{}", e),
        }
        i += 1;
        println!("Fetching data: {i}/1025");
    }

    species
}

fn write_xlsl(pokemon: &Vec<Candidate>) -> Result<(), XlsxError> {
    let mut workbook = Workbook::new();
    let worksheet = workbook.add_worksheet();

    worksheet.write(0, 0, "Index")?;
    worksheet.write(0, 1, "ID")?;
    worksheet.write(0, 2, "Pokemon")?;
    worksheet.write(0, 3, "No. of stages")?;

    let mut i = 1;
    for item in pokemon {
        worksheet.write(i, 0, i)?;
        worksheet.write(i, 1, item.0)?;
        worksheet.write(i, 2, item.1.clone())?;
        worksheet.write(i, 3, item.2)?;
        i += 1;
    }

    workbook.save("demo.xlsx")?;
    Ok(())
}
