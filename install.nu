#!/usr/bin/env nu

def api [type: string] {
  match $type {
    "png" => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    "gif" => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/"
  }
}

let target = "target/"
let types = [ "png" "gif" ]

mkdir $target
for type in $types {
  mkdir ($target + $type)

  let api = (api $type)
  let dir = $"($target)($type)/"

  1..151 | par-each {|id|
    let path = $"($dir)($id).($type)"
    print $"Copying into ($path)..."
    http get $"($api)/($id).($type)" | save -f $path
  }
}