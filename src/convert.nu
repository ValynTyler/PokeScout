#!/usr/bin/env nu

def resize [input: string, output: string] {
  magick $input -coalesce -background transparent -gravity center -extent 96x96 $output
}

def main [
  source?: string = "target/gif/original"
  target?: string = "target/gif/96x96"
] {
  mkdir $target

  1..649 | par-each {|id|
    let name = $"($id).gif"
    let source_path = $"($source)/($name)"
    let target_path = $"($target)/($name)"

    if ($"($source)/($name)" | path exists) {
      print $"Resizing ($source_path)..."
      resize $source_path $target_path
    } else {
      print $"Could not find ($source_path). Skipping..."
    }
  }

  print "Rename operation successful!"
}