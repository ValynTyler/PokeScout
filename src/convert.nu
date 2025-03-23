#!/usr/bin/env nu

def resize [input: string, output: string] {
  magick $input -coalesce -background transparent -gravity center -extent 96x96 $output
}

let target = $"($env.FILE_PWD)/../target/"
let type = "gif"

1..649 | par-each {|id|
  let path = $"($target)($type)/($id).($type)"
  print $"Resizing ($path)..."
  resize $path $path
}