/*
Name: Chris Vo
Date: October 5, 2018
Class: CS 134 - Kevin Smith
This class defines the ofApp class/object with proposable variables and functions.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#pragma once

#include "ofMain.h"
#include "ofxGui.h"
#include "Particle.h"
#include "ParticleSystem.h"
#include "Emitter.h"

class ofApp : public ofBaseApp{

	public:
		Emitter player; //missile launcher that the user controls
		Emitter spawn;
		ParticleEmitter particles;
		Sprite laser;
		Sprite enemy; //enemy ship that only moves around
		ofVec3f last;
		ofVec3f move;
		MoveDir arrow; //to record which arrow keys was pressed to update movement accordingly
		bool start; //check if the game is in idle or playable mode
		bool flag; //to clear the enemy spawns when the player dies. Recreate the spawns when the user plays again
		int score;
		int life;
		int level;
		ofSoundPlayer sound1;
		ofSoundPlayer sound2;
		Attributes playerAttributes;
		Attributes enemyAttributes;
		Attributes ParticleAttributes;
		ofImage background;

		void setup();
		void restart();
		void setAttributes();
		void gameStart();
		void update();
		void draw();
		void keyPressed(int key);
		void keyReleased(int key);
		void mouseMoved(int x, int y );
		void mouseDragged(int x, int y, int button);
		void mousePressed(int x, int y, int button);
		void mouseReleased(int x, int y, int button);
		void mouseEntered(int x, int y);
		void mouseExited(int x, int y);
		void windowResized(int w, int h);
		void dragEvent(ofDragInfo dragInfo);
		void gotMessage(ofMessage msg);

		ofLight light;
};
