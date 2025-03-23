#!/usr/bin/env nu

def main [input: string, output: string] {
  magick $input -coalesce -background transparent -gravity center -extent 96x96 $output
}