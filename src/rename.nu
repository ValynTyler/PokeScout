#!/usr/bin/env nu

def main [
  source?: string = "target/gif/96x96"
  target?: string = "target/gif/96x96_renamed"
  type?: string = "gif"
  prefix?: string = "#"
  suffix?: string = ""
] {
  mkdir $target

  1..1025 | par-each {|id|
    let old_name = $"($id).($type)"
    let new_name = $"($prefix)($id)($suffix).($type)"

    let source_path = $"($source)/($old_name)"
    let target_path = $"($target)/($new_name)"

    if ($source_path | path exists) {
      print $"Copying ($source_path) to ($target_path)..."
      cp $source_path $target_path
    } else {
      print $"Could not find ($source_path). Skipping..."
    }
  }

  print "Rename operation successful"
}