#!/usr/bin/env nu

let target = $"($env.FILE_PWD)/../target/"
let types = [ "png" "gif" ]
let prefix = "pokemon_"

$types | par-each {|type|
  let dir = $"($target)($type)/"
  1..1025 | par-each {|id|
    let prev = $"($dir)($id).($type)"
    let file = if $type == "gif" {
      $"($dir)($prefix)($id)_anim.($type)"
    } else {
      $"($dir)($prefix)($id).($type)"
    }
    mv $prev $file
  }
}

print "Rename successfull!"