/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file runs the entire program
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "ofMain.h"
#include "ofApp.h"

//========================================================================
int main( ){
	ofSetupOpenGL(750,1334,OF_WINDOW);			// <-------- setup the GL context

	// this kicks off the running of my app
	// can be OF_WINDOW or OF_FULLSCREEN
	// pass in width and height too:
	ofRunApp(new ofApp());

}
