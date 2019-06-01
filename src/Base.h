/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file defines a base class. It shares similar functionalities between the other classes.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "ofMain.h"

//labeling each arrow keys in the keyboard
typedef enum { Left, Right, Up, Down, Stop } MoveDir;

class Base {
	public:
		ofVec3f position;
		ofVec3f velocity;
		float minX;
		float minY;
		float maxX;
		float maxY;

		void setBounds(float x1, float x2, float y1, float y2) { minX = x1; minY = y1, maxX = x2, maxY = y2; };
		void setPosition(ofVec3f v) { position = v; };
		void setVelocity(ofVec3f v) { velocity = v; };
};