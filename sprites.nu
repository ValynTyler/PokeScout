#!/usr/bin/env nu

let target = $env.FILE_PWD + "/target"

def types [] { [ gif png ] }

def api [type: string@types] {
  match $type {
    gif => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated"
    png => "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"
  }
}

def count [type: string@types] {
  match $type {
    gif => 649
    png => 1025
  }
}

def fetch [
  id: int
  type: string@types
  path: string
] {
  let name = $"($id).($type)"
  let api = api $type

  http get $"($api)/($name)" | save -f $path
}

def resize [
  input: string,
  output: string
] {
  magick $input -coalesce -background transparent -gravity center -extent 96x96 $output
}

def fetch-all [
  target: string,
] {
  print "Fetching images..."
  types | each {|type|
    1..(count $type) | par-each {|id|
      let name = $"($id).($type)"
      let stem = [ $target $type ] | path join
      let path = [ $stem $name ] | path join

      print $"Fetching ($name)"
      mkdir $stem
      fetch $id $type $path
    }
  }
}

def resize-all [
  source: string,
  target: string,
] {
  print "Resizing images..."
  types | each {|type|
    1..(count $type) | par-each {|id|
      let name = $"($id).($type)"
      let source_stem = [ $source $type ] | path join
      let target_stem = [ $target $type ] | path join
      let source_path = [ $source_stem $name ] | path join
      let target_path = [ $target_stem $name ] | path join

      print $"Resizing ($name)"
      mkdir $target_stem
      resize $source_path $target_path
    }
  }
}

def rename-all [
  source: string,
  target: string,
] {
  print "Renaming images..."
  types | each {|type|
    1..(count $type) | par-each {|id|
      let prefix = match $type {
        gif => 'a'
        png => 's'
      }

      let old_name = $"($id).($type)"
      let new_name = $"($prefix)($id).($type)"

      let source_stem = [ $source $type ] | path join
      let target_stem = [ $target $type ] | path join
      let source_path = [ $source_stem $old_name ] | path join
      let target_path = [ $target_stem $new_name ] | path join

      print $"Renaming ($old_name)"
      mkdir $target_stem
      cp $source_path $target_path
    }
  }
}

def main [] {
  let origin_stem = ($target | path join "origin")
  let resize_stem = ($target | path join "resize")
  let rename_stem = ($target | path join "rename")

  # fetch-all $origin_stem
  # resize-all $origin_stem $resize_stem
  rename-all $resize_stem $rename_stem

  print "Sprites installed successfully!"
}