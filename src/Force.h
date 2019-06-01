/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file describes a force class as well as several forces classes. This segment of code is from 
Kevin M. Smith - CS 134 SJSU
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "Particle.h"

class Force {
	public:
		bool applyOnce = false;
		bool applied = false;
		virtual void updateForce(Particle *) = 0;
};

class GravityForce : public Force {
	ofVec3f gravity;
public:
	GravityForce(const ofVec3f &);
	void updateForce(Particle *);
};


class TurbulenceForce : public Force {
	ofVec3f tmin, tmax;
public:
	TurbulenceForce(const ofVec3f & min, const ofVec3f &max);
	void updateForce(Particle *);
};

class RandomForce : public Force {
	float magnitude;
public:
	RandomForce(float);
	void updateForce(Particle *);
};
