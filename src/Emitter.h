/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This class defines the emitter class/object with proposable variables and functions.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "ofMain.h"
#include "ParticleSystem.h"

typedef enum { Radial, Directional } EmitterType;

//define a sprite's attributes
struct Attributes {
	ofVec3f position;
	ofVec3f velocity;
	float birth;
	float lifespan;
};

//emitter is a sub-class of sprite so emitters can be used in SpriteSystem (e.g. to spawn enemies that can fire back)
class Emitter: public Sprite {
	public:
		SpriteSystem *sprites;
		ofImage childImage;
		ofImage image;
		int rate;
		bool state;
		Attributes attributes; //to save in a sprite's attributes for the given emitter
		float oldtime;

		Emitter();
		void draw();
		void start();
		void stop();
		void setImage(string);
		void setChildImage(string);
		void setRate(float);
		void setAttributes(Attributes); //set a sprite's attributes with attributes for outside source e.g. sliders
		void updateSprite(Sprite);
		void updatePosition(ofVec3f);
		ofVec3f updatePosition(MoveDir);
		bool inside(ofVec2f);
};

class ParticleEmitter : public Emitter {
	public:
		ParticleSystem *particles;
		int groupSize;
		EmitterType type;

		ParticleEmitter();
		void update();
};