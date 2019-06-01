/*
Name: Chris Vo
Date: November 4, 2018
Class: CS 134 - Kevin Smith
This file creates the foundation and functionalities of the game. The user controls an emitter that is able to fire (create)
sprites using the spacebar. Enemy sprites are spawned in various locations and can be moved differently based on
slider values. Basic collision detection is implemented so that whenever a missile encounters an enemy, the missile
and the enemy disappears. Additionally, when the player touches an enemy, the game is over and restarted. All
functionalities in this game is fully modifiable (e.g. spawn points can be placed anywhere in the play area). Physics
and particle effects are also implemented to enhance gameplay. Different levels increasing difficulty is also added.
*/

/*
SYNOPSIS: You are Stickman, stick figure superhero and defender of Stick Figure World. You must defend your
beloved home planet from your evil clones in outer space. Do you have what it takes?
*/
#include "ofApp.h"
#include<cmath>

//--------------------------------------------------------------
void ofApp::setup(){
	start = false; //to prevent the user from controlling the emitter
	player.setImage("images/loading-screen.png"); //shows a message indicating that the game is in idle mode
	background.loadImage("images/game-background.png");
	player.setPosition(ofVec3f(ofGetWindowWidth() / 2, ofGetWindowHeight() / 2)); //sets to center of the screen
	arrow = Stop;
	flag = false;
	level = 1;
	score = 0;
	life = 3;
	sound1.loadSound("sounds/oof.mp3");
	sound2.loadSound("sounds/pew.mp3");
	particles.groupSize = 100;
	particles.type = Radial;
}

//this function is to reset the values of the player and enemies. After the game is over and restarted, attributes
//for the player's and enemies' sprites are set to zero.
void ofApp::restart() {
	start = false; //to prevent the user from controlling the emitter
	player.setImage("images/loading-screen.png"); //shows a message indicating that the game is in idle mode
	player.setPosition(ofVec3f(ofGetWindowWidth() / 2, ofGetWindowHeight() / 2)); //sets to center of the screen
	arrow = Stop;
	playerAttributes.birth = 0;
	playerAttributes.lifespan = 0;
	playerAttributes.position = ofVec3f(0, 0, 0);
	playerAttributes.velocity = ofVec3f(0, 0, 0);
	enemyAttributes.birth = 0;
	enemyAttributes.lifespan = 0;
	enemyAttributes.position = ofVec3f(0, 0, 0);
	enemyAttributes.velocity = ofVec3f(0, 0, 0);
	score = 0;
	life = 3;
	level = 1;
}

//this function is to set all the attributes of the player and enemies' sprites in the Sprite System. 
//This function pretty much reduce the size of code in the update function
void ofApp::setAttributes() {
	playerAttributes.position = (player.position - ofVec2f(0, player.image.getHeight() / 2)); //the missiles will spawn above the emitter
	playerAttributes.birth = ofGetSystemTimeMillis();
	playerAttributes.lifespan = 50 * 100; //tentative
	playerAttributes.velocity = ofVec3f(0, -1200, 0);
	ParticleAttributes.birth = ofGetSystemTimeMillis();
	ParticleAttributes.lifespan = 2000;
	ParticleAttributes.velocity = ofVec3f(0, 240);

	enemyAttributes.birth = ofGetSystemTimeMillis();
	enemyAttributes.lifespan = 10 * 600; //tentative
	enemyAttributes.position = (spawn.position + ofVec2f(0, player.image.getHeight() / 2));
	spawn.setAttributes(enemyAttributes);
}

//this function switches the game from idle mode to playable mode and sets up the game 
//This function essentially shortens the size of code in the keypressed function
void ofApp::gameStart() {
	player.setImage("images/stick-figure-player.png");
	player.setChildImage("images/laser-beams.png");
	player.lifespan = 1;
	player.setVelocity(ofVec3f(0, 240));
	player.setPosition(ofVec3f(ofGetWindowWidth() / 2, ofGetWindowHeight() / 2));
	player.setRate(500);
	spawn.setRate(1000);
	enemyAttributes.velocity = ofVec3f(0, 240);

	spawn.setChildImage("images/stick-figure-enemy.png");

	particles.particles->addForce(new GravityForce(ofVec3f(0, -480, 0)));
}

//--------------------------------------------------------------
void ofApp::update(){
	if(start) setAttributes(); //to prevent spawning enemy sprites or other disasters during idle mode
	player.setAttributes(playerAttributes);
	player.updateSprite(laser);
	player.sprites->update();
	player.updatePosition(move);

	spawn.setPosition(ofVec3f(ofRandom(0, ofGetWindowWidth()), -20));

	for (int i = 0; i < spawn.sprites->particles.size(); i++) {
		//distance between an enemy sprite and the player
		ofVec3f playerDistance = ofVec3f(abs(spawn.sprites->particles[i].position.x - player.position.x), abs(spawn.sprites->particles[i].position.y - player.position.y));
		float playerContact1 = (player.image.getWidth() / 2) + (spawn.sprites->particles[i].image.getWidth() / 2);
		float playerContact2 = (player.image.getHeight() / 2) + (spawn.sprites->particles[i].image.getHeight() / 2);

		if (playerDistance.x < playerContact1 && playerDistance.y < playerContact2) {
			life--;
			spawn.sprites->particles[i].lifespan = 0;

			if (life < 1) {
				spawn.sprites->forces.clear();
				spawn.stop();

				for (int j = 0; j < spawn.sprites->particles.size(); j++)
					spawn.sprites->particles[j].lifespan = 0;

				for (int j = 0; j < particles.particles->particles.size(); j++)
					particles.particles->particles[j].lifespan = 0;

				restart();
				flag = true;
			}
		}

		for (int j = 0; j < player.sprites->particles.size(); j++) {
			//distance between an enemy sprite and the player's missile sprite
			ofVec3f missileDistance = ofVec3f(abs(spawn.sprites->particles[i].position.x - player.sprites->particles[j].position.x), abs(spawn.sprites->particles[i].position.y - player.sprites->particles[j].position.y));
			float missileContact1 = (player.sprites->particles[j].image.getWidth() / 2) + (spawn.sprites->particles[i].image.getWidth() / 2);
			float missileContact2 = (player.sprites->particles[j].image.getHeight() / 2) + (spawn.sprites->particles[i].image.getHeight() / 2);
			if (missileDistance.x < missileContact1 && missileDistance.y < missileContact2) {
				particles.attributes = ParticleAttributes;
				particles.position = spawn.sprites->particles[i].position;
				particles.start();
				spawn.sprites->particles[i].lifespan = 0; //set lifespan value to 0 to immediately remove it in the system
				player.sprites->particles[j].lifespan = 0;
				score += 100;

				if (score == 4000) {
					spawn.sprites->addForce(new RandomForce(2500));
				}


				if (score == 9000) {
					spawn.sprites->addForce(new RandomForce(5000));
				}

				//every score of 5000 nets an extra life and increases player missile and spawn rate and speed by a bit
				if (score % 1000 == 0) {
					life++;
					player.setRate(player.rate - 5);
					spawn.setRate(spawn.rate - 50);
					enemyAttributes.velocity += ofVec3f(0, 10);
					level++;
				}

				sound1.play();
			}
		}
	}

	spawn.updateSprite(enemy);
	spawn.sprites->update();
	particles.update();
	particles.particles->update();

	//if player dies, clear the spawns to prevent duplicate spawns upon restarting the game
	if (flag) {
		flag = false;
	}
}

//--------------------------------------------------------------
void ofApp::draw(){
	//no need to draw the enemy spawns since they are offscreen
	background.draw(0, 0);
	player.draw();
	player.sprites->draw();

	spawn.sprites->draw();
	particles.particles->draw();

	string str1;
	str1 += "Score: " + std::to_string(score);
	ofDrawBitmapString(str1, 200, 55);

	string str2;
	str2 += "Life: " + std::to_string(life);
	ofDrawBitmapString(str2, 20, 55);

	string str3;
	str3 += "Level: " + std::to_string(level);
	ofDrawBitmapString(str3, 100, 55);
}

//--------------------------------------------------------------
void ofApp::keyPressed(int key){
	if (key == ' ') {
		if (!start) { //if it is still in idle mode
			gameStart();

			spawn.start();

			start = true; //game's no longer in idle mode
		}
		else { //if it is in game mode (not in idle mode)
			player.start();
			sound2.play();
		}
	}
	
	if (start) {
		switch (key) { //sets the value based on the directions of the arrow keys
			case OF_KEY_RIGHT:
				arrow = Right;
				break;
			case OF_KEY_LEFT:
				arrow = Left;
				break;
			case OF_KEY_UP:
				arrow = Up;
				break;
			case OF_KEY_DOWN:
				arrow = Down;
				break;
		};
	}

	move = player.updatePosition(arrow); //gets the value to move the player sprite in the Update function
}

//--------------------------------------------------------------
void ofApp::keyReleased(int key){
	if (start) {
		switch (key) { //sets the value based on the directions of the arrow keys
			case OF_KEY_RIGHT:
			case OF_KEY_LEFT:
			case OF_KEY_UP:
			case OF_KEY_DOWN:
				arrow = Stop;
				break;
			case ' ':
				player.stop();
				break;
		};

		move = player.updatePosition(arrow); //gets the value to move the player sprite in the Update function
	}
}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y ){

}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button){
	if (start) {
		if (player.inside(last)) { //if the mouse click is within the emitter's image area
			ofVec3f current;
			current.set(x, y);
			//if the player sprite is within the play area, update the position of the sprite, else do not update.
			if (current.x > (0 + 40) && current.x < (ofGetWindowWidth() - 40) && current.y > (0 + player.image.getHeight()) && current.y < (ofGetWindowHeight() - player.image.getHeight())) {
				player.position += current - last;
				last = current;
			}
		}
	}
}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button){
	last.set(x, y);
}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mouseEntered(int x, int y){

}

//--------------------------------------------------------------
void ofApp::mouseExited(int x, int y){

}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h){

}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg){

}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo){ 

}
