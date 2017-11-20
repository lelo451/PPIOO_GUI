#!/bin/bash
max=151
a='wget https://assets.pokemon.com/assets/cms2/img/pokedex/detail/'
b='.png'
for i in `seq -w 1 $max`
do
  echo $i
  $a$i$b
done
