#!/bin/bash

cnt=$(ls -1 dot | wc -l)
i=1

for f in dot/*; do
    echo -en "\r$((100*i/cnt))% converting $f now...                               "
    i=$((i+1))
    fdp "$f" -Tpng -o"${f/.dot/.png}"
done
echo

mkdir -p png
mv dot/*.png png
echo done