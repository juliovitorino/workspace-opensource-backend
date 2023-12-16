#!/bin/zsh

echo "Cleaning codegen for Notifier"

cd ~/workspaces/workspace-opensource-backend/notifier/src/main/java/br/com/jcv/notifier
rm analyser/*
rm builder/*
rm config/*
rm constantes/*
rm controller/*
rm dto/*
rm enums/*
rm exception/*
rm interfaces/*
rm repository/*
rm service/impl/*
rm service/*

echo "codegen files were deleted!"