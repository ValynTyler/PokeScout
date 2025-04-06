#!/usr/bin/env nu

let target = $env.FILE_PWD + "/target"
let types = [ gif png ]

def api [type: string] {
  match $type {
    gif => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated"
    png => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"
  }
}

def image [
  id: int
  type: string
  path: string
] {
  let name = $"($id).($type)"
  let api = api $type

  http get $"($api)/($name)" | save -f $"($path)/($name)"
}

def resize [
  input: string,
  output: string
] {
  magick $input -coalesce -background transparent -gravity center -extent 96x96 $output
}

def suffix [type: string] {
  match $type {
    gif => "_anim"
    png => ""
  }
}

def format [id: int, type: string] {
  let prefix = "number_"
  let suffix = suffix $type

  $"($prefix)($id)($suffix).($type)"
}

def main [] {
  mkdir $target

  1..1025 | par-each {|id|
    $types | par-each {|type|
      let name = $"($id).($type)"
      let path = $"($target)/($name)"
      let format_path = $"($target)/(format $id $type)"

      print $"Copying ($name)..."
      image $id $type $target

      print $"Resizing ($name)..."
      resize $path $path

      print $"Writing to ($format_path)"
      mv $path $format_path
    }
  }

  print "Sprites installed successfully!"
}