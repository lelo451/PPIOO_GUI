#!/bin/bash
xelatex -interaction=nonstopmode documentacao.tex
xelatex -interaction=nonstopmode documentacao.tex
rm *.log *.aux
