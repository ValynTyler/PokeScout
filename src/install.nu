#!/usr/bin/env nu

def api [type: string] {
  match $type {
    "png" => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"
    "gif" => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated"
  }
}

def main [
  target?: string = target
  types?: list<string> = [ png gif ]
] {
  let root = $"($env.FILE_PWD)/../($target)"

  $types | par-each {|type|
    let api = (api $type)
    let dir = $"($root)/($type)/original"

    mkdir $dir

    1..1025 | par-each {|id|
      let name = $"($id).($type)"
      let path = $"($dir)/($name)"

      let res = http get -e -f $"($api)/($name)"
      match $res.status {
        200 => {
          print $"Copying into ($path)..."
          $res.body | save -f $path
        }
        _ => {
          print $"Could not find ($name). Skipping..."
        }
      }
    }
  }

  print "Installation successfull!"
}