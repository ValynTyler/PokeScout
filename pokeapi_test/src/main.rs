use core::panic;
use std::sync::Arc;

use rustemon::{client::RustemonClient, model::pokemon::PokemonSpecies, pokemon::pokemon_species};
use tokio::{sync::Mutex, task};

#[tokio::main]
async fn main() {
    let rustemon_client: Arc<Mutex<RustemonClient>> =
        Arc::new(Mutex::new(RustemonClient::default()));

    let mut cnt = 0;

    for item in get_all_species(&rustemon_client).await {
        if !item.is_legendary
        && !item.is_mythical
        && item.evolves_from_species == None {
            cnt += 1;
            println!("{} {}", item.id, item.name)
        }
    }

    println!("Total amount: {}", cnt);
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