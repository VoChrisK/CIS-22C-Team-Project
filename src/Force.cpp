/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file describes the function of the forces classes. This segment of code is from Kevin M. Smith - CS 134 SJSU
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "force.h"

GravityForce::GravityForce(const ofVec3f &g) {
	gravity = g;
}

void GravityForce::updateForce(Particle *particle) {
	particle->forces += gravity * particle->mass;
}

TurbulenceForce::TurbulenceForce(const ofVec3f &min, const ofVec3f &max) {
	tmin = min;
	tmax = max;
}

void TurbulenceForce::updateForce(Particle * particle) {
	particle->forces.x += ofRandom(tmin.x, tmax.x);
	particle->forces.y += ofRandom(tmin.y, tmax.y);
}

RandomForce::RandomForce(float magnitude) {
	this->magnitude = magnitude;
}

void RandomForce::updateForce(Particle * particle) {
	ofVec3f dir = ofVec3f(ofRandom(-100, 100), ofRandom(-10, 10), 0);
	particle->forces += dir.getNormalized() * magnitude;
}