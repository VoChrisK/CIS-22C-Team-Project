/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file defines the functions of the ParticleSystem and SpriteSystem object/class. The SpriteSystem is a collection
of sprites and have functions that changes the number of sprites within the collection.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "ParticleSystem.h"

//this function adds a sprite to the collection of sprites
void ParticleSystem::add(Particle particle) {
	particles.push_back(particle);
}

//this functions draws all current sprites within the collection
void ParticleSystem::draw() {
	for (int i = 0; i < particles.size(); i++) {
		particles[i].draw();
	}
}

void ParticleSystem::addForce(Force *force) {
	forces.push_back(force);
}

void ParticleSystem::reset() {
	for (int i = 0; i < forces.size(); i++) {
		forces[i]->applied = false;
	}
}

void ParticleSystem::update() {

	if (particles.size() == 0) return;

	//to iterate through the collection of sprites
	vector<Particle>::iterator p = particles.begin();
	vector<Particle>::iterator tmp;

	while (p != particles.end()) {
		if (p->lifespan != -1 && p->age() > p->lifespan) {
			tmp = particles.erase(p);
			p = tmp;
		}
		else p++;
	}

	for (int i = 0; i < particles.size(); i++) {
		for (int k = 0; k < forces.size(); k++) {
			if (!forces[k]->applied)
				forces[k]->updateForce(&particles[i]);
		}
	}

	// update all forces only applied once to "applied"
	// so they are not applied again.
	//
	for (int i = 0; i < forces.size(); i++) {
		if (forces[i]->applyOnce)
			forces[i]->applied = true;
	}

	// integrate all the particles in the store
	//
	for (int i = 0; i < particles.size(); i++)
		particles[i].integrate();
}

//this function adds a sprite to the collection of sprites
void SpriteSystem::add(Sprite sprite) {
	particles.push_back(sprite);
}

void SpriteSystem::addForce(Force *force) {
	forces.push_back(force);
}

//this functions draws all current sprites within the collection
void SpriteSystem::draw() {
	for (int i = 0; i < particles.size(); i++) {
		particles[i].draw();
	}
}

void SpriteSystem::reset() {
	for (int i = 0; i < forces.size(); i++) {
		forces[i]->applied = false;
	}
}

//this function checks which sprite have expended their lifespan. Sprites are deleted when they
//are found. An iterator object is used to traverse through the collection of sprites
//This segment of code is from Kevin M. Smith - CS 134 SJSU
void SpriteSystem::update() {

	if (particles.size() == 0) return;

	//to iterate through the collection of sprites
	vector<Sprite>::iterator s = particles.begin();
	vector<Sprite>::iterator tmp;

	while (s != particles.end()) {
		if (s->lifespan != -1 && s->age() > s->lifespan) {
			tmp = particles.erase(s);
			s = tmp;
		}
		else s++;
	}

	if (!forces.empty()) {
		for (int i = 0; i < particles.size(); i++) {
			for (int k = 0; k < forces.size(); k++) {
				if (!forces[k]->applied)
					forces[k]->updateForce(&particles[i]);
			}
		}

		// update all forces only applied once to "applied"
		// so they are not applied again.
		//
		for (int i = 0; i < forces.size(); i++) {
			if (forces[i]->applyOnce)
				forces[i]->applied = true;
		}

		// integrate all the particles in the store
		//
		for (int i = 0; i < particles.size(); i++)
			particles[i].integrate();
	}

	//move sprite
	for (int i = 0; i < particles.size(); i++) {
		particles[i].position += particles[i].velocity / ofGetFrameRate();
	}
}