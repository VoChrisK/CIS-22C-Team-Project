/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file describes the functions of the emitter object/class. In addition to having similar
attributes to a sprite, the emitter object have functions that spawns sprites and control their
rate of creation. Additionally it contains a dynamic array of sprites to hold the sprites in.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "Emitter.h"

//constructor: set values to 0 and create a new SpriteSystem - collection of sprites. Also
//sets other variables to zero or equivalent
Emitter::Emitter() {
	setPosition(ofVec3f(0, 0));
	sprites = new SpriteSystem;
	oldtime = ofGetSystemTimeMillis();
}

//this function draws the emitter
void Emitter::draw() {
	image.draw(-image.getWidth() / 2.0 + position.x, -image.getHeight() / 2.0 + position.y);
}

//this function allows the emitter to spawn new sprites
void Emitter::start() {
	state = true;
}

//this function prevents the emitter from spawning new sprites
void Emitter::stop() {
	state = false;
}

//this function sets the image of the emitter
void Emitter::setImage(string s) {
	image.loadImage(s);
}

//this function sets the image of the sprites
void Emitter::setChildImage(string s) {
	childImage.loadImage(s);
}

//this function sets the rate of creation for the sprites
void Emitter::setRate(float r) {
	rate = r;
}

//this function sets the attributes for the sprites when they are created
void Emitter::setAttributes(Attributes a) {
	attributes = a;
}

//this function essentially creates a new sprite by setting its attributes and adds it to the
//SpriteSystem. It also checks for rate of creation so each sprite is created at a fixed rate.
void Emitter::updateSprite(Sprite sprite) { //parameter to differentiate between sprite or emitter
	float currenttime = ofGetSystemTimeMillis();
	if (state) {
		if ((currenttime - oldtime) >= rate) {
			sprite.image = childImage;
			sprite.birth = attributes.birth;
			sprite.lifespan = attributes.lifespan;
			sprite.position = attributes.position;
			sprite.velocity = attributes.velocity;
			sprites->add(sprite);
			oldtime = currenttime;
		}
	}
}

//this function updates the position of the emitter.
void Emitter::updatePosition(ofVec3f v) {
	position += v;
}

//this overloaded function sets the value to translate the position of the emitter.
//It also restricts the emitter from moving out of bounds(play area).
ofVec3f Emitter::updatePosition(MoveDir dir) {
	ofVec3f v;
	float dist = velocity.y / ofGetFrameRate();

	//sets the value based on the directions of the arrow keys
	switch (dir) {
		case Left:
			if(position.x > (0 + image.getWidth())) {
				v.set(-dist, 0, 0);
				break;
			}
		case Right:
			if (position.x < (ofGetWindowWidth() - image.getWidth())) {
				v.set(dist, 0, 0);
				break;
			}
		case Up:
			if (position.y > (0 + image.getHeight())) {
				v.set(0, -dist, 0);
				break;
			}
		case Down:
			if (position.y < (ofGetWindowHeight() - image.getHeight())) {
				v.set(0, dist, 0);
				break;
			}
	};

	return v;
}

//this method checks if the vector point is within the emitter's image area
bool Emitter::inside(ofVec2f v) {
	setBounds(position.x - image.getHeight() / 2, position.x + image.getHeight() / 2, position.y - image.getWidth() / 2, position.y + image.getWidth() / 2);

	if (v.x > minX && v.x < maxX && v.y > minY && v.y < maxY)
		return true;

	return false;
}

ParticleEmitter::ParticleEmitter() {
	particles = new ParticleSystem;
	type = Directional;
}

void ParticleEmitter::update() {
	ofColor colors[] = { ofColor::red, ofColor::yellow, ofColor::blue, ofColor::green, ofColor::orange, ofColor::purple, ofColor::brown, ofColor::cyan, ofColor::pink, ofColor::aqua, ofColor::fuchsia };

	float currenttime = ofGetSystemTimeMillis();
	Particle particle;
	if (state) {
		if ((currenttime - oldtime) >= rate) {
			for (int i = 0; i < groupSize; i++) {
				particle.birth = attributes.birth;
				particle.lifespan = attributes.lifespan;
				int index = ofRandom(0, 10);
				particle.color = colors[index];

				switch (type) {
				case Directional:
					particle.velocity = attributes.velocity;
					particle.position.set(position);
					break;
				case Radial:
					//ofVec3f dir = ofVec3f(ofRandom(-1, 1), ofRandom(-1, 1));
					float speed = attributes.velocity.length();
					particle.velocity = ofVec3f(ofRandom(-0.25, 0.25), ofRandom(-1, 0)) * speed;
					particle.position.set(position);
					break;
				}

				particles->add(particle);
				oldtime = currenttime;
			}
			stop();
		}
	}
}