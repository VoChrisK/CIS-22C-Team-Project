/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file declares a particle and sprite class/object with proposable variables and methods
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "ofMain.h"
#include "Base.h"

class Particle: public Base {
	public:
		float birth;
		float lifespan;
		ofVec3f forces;
		ofVec3f acceleration;
		float damping;
		float mass;
		ofImage image;
		ofColor color;

		Particle();
		double age();
		void updatePosition(ofVec3f);
		virtual void draw();
		void integrate();
};

class Sprite : public Particle {
	public:
		
		Sprite();
		void draw();
		bool inside(ofVec2f);
};