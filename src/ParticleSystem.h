/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file defines the ParticleSystem and SpriteSystem object/class with proposed variables and functions
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "ofMain.h"
#include "Particle.h"
#include "Force.h"

class ParticleSystem {
	public:
		vector<Particle> particles;
		vector<Force *> forces;

		void add(Particle);
		void addForce(Force *);
		void draw();
		void update();
		void reset();
};

class SpriteSystem {
	public:
		vector<Sprite> particles;
		vector<Force *> forces;

		void add(Sprite);
		void addForce(Force *);
		void draw();
		void update();
		void reset();
};