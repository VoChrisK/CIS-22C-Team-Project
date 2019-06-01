/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file describes the functions of the Particle and Sprite class/object. A sprite will have defined
position, birth of creation, lifespan, and image while a particle will have those except image as well
as mass, acceleration, and damping.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "Particle.h"

Particle::Particle() {
	acceleration.set(0, 0, 0);
	damping = 1;
	mass = 1;
	color = ofColor::red;
}

//this function calculates the age of the sprite
double Particle::age() {
	return (ofGetSystemTimeMillis() - birth);
}

//this function updates the position of the sprite
void Particle::updatePosition(ofVec3f v) {
	position += v;
}

void Particle::draw() {
	ofSetColor(color);
	ofDrawCircle(position.x, position.y, 1);
	ofSetColor(ofColor::white);
}

void Particle::integrate() {
	float dt = 1.0 / ofGetFrameRate();

	position += (velocity * dt);

	ofVec3f accel = acceleration;
	accel += (forces * (1.0 / mass));
	velocity += accel * dt;

	velocity *= damping;

	forces.set(0, 0, 0);
}

//constructor: set the position to 0, set its bounds based on the size of the image, and set its birth to current time elapsed
Sprite::Sprite() {
	setPosition(ofVec3f(0, 0));
	setVelocity(ofVec3f(0, 0));
	setBounds(position.x - image.getHeight() / 2, position.x + image.getHeight() / 2, position.y - image.getWidth() / 2, position.y + image.getWidth() / 2);
	lifespan = 0;
	birth = ofGetSystemTimeMillis();
}

//this function checks if the corresponding vector point is within the sprite
bool Sprite::inside(ofVec2f vector) {
	//if its inside the sizable area of the sprite
	if (vector.x > minX && vector.x < maxX && vector.y > minY && vector.y < maxY)
		return true;

	return false;
}

//this method draws the sprite
void Sprite::draw() {
	image.draw(-image.getWidth() / 2.0 + position.x, -image.getHeight() / 2.0 + position.y);
}