import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Pacman extends PApplet {

Sprite pacman = new Sprite("https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Circle_Citrine_Solid.svg/768px-Circle_Citrine_Solid.svg.png", 600, 400, 25, 25);
boolean up, down, left, right;
int moveQueue = 0; // 1 = up, 2 = down, 3 = left, 4 = right

ArrayList<Sprite> friends = new ArrayList<Sprite>();

int screen = 0;

int timer = 0;

int[] spawnX = {
  700, 
  520, 
  534, 
  620, 
  680, 
  700, 
  500, 
  500
};

int[] spawnY = {
  540, 
  285, 
  434, 
  360, 
  205, 
  390, 
  545, 
  395
};

Sprite[] walls = {
  new Sprite(600, 418, 100, 10), 
  new Sprite(600, 382, 100, 10), 
  new Sprite(682, 400, 10, 115), 
  new Sprite(518, 400, 10, 115), 
  new Sprite(660, 345, 54, 10), 
  new Sprite(540, 345, 54, 10), 
  new Sprite(600, 454, 174, 10), 
  new Sprite(600, 342, 10, 80), 
  new Sprite(600, 491, 100, 10), 
  new Sprite(600, 513, 10, 40), 
  new Sprite(600, 268, 120, 10), 
  new Sprite(600, 220, 120, 10), 
  // dupered
  new Sprite(522, 307, 90, 10), 
  new Sprite(482, 340, 10, 60), 
  new Sprite(462, 375, 50, 10), 
  new Sprite(462, 412, 50, 10), 
  new Sprite(482, 487, 10, 150), 
  new Sprite(502, 491, 40, 10), 
  new Sprite(540, 528, 50, 10), 
  new Sprite(500, 245, 10, 130), 
  new Sprite(538, 244, 10, 58), 
  new Sprite(527, 565, 100, 10), 
  new Sprite(582, 585, 10, 50), 
  new Sprite(541, 182, 92, 10), 
  new Sprite(582, 162, 10, 50), 
  new Sprite(678, 307, 90, 10), 
  new Sprite(718, 340, 10, 60), 
  new Sprite(738, 375, 50, 10), 
  new Sprite(738, 412, 50, 10), 
  new Sprite(718, 487, 10, 150), 
  new Sprite(698, 491, 40, 10), 
  new Sprite(660, 528, 50, 10), 
  new Sprite(700, 245, 10, 130), 
  new Sprite(662, 244, 10, 58), 
  new Sprite(673, 565, 100, 10), 
  new Sprite(618, 585, 10, 50), 
  new Sprite(659, 182, 92, 10), 
  new Sprite(618, 162, 10, 50), 
  //new Sprite(575, 245, 10, 60), 
  //new Sprite(625, 245, 10, 60), 
};

String[] facts = {
  "You can actually spread the virus even if you show no symptoms at all!", 
  "People who have contracted COVID-19 but have\nrecovered pose no harm at all to others.", 
  "Surgical masks actually do not do much to keep things out,\nthey do a better job at keeping things in.\nIf you are sick or coughing, wear a mask.\nBut, constantly wearing a mask if you are well is not necessary.", 
  "The word quarantine stems from the Italian word for forty,\nrepresenting the number of days persons potentially exposed\n to communicable diseases had to remain in isolation.", 
  "Approximately 828 thousand people have\nrecovered after contracting COVID-19.", 
  "The number of people who have recovered\nfrom COVID-19 is greater than the \nnumber of people who have died.",
  "The growth of COVID-19 is exponential,\n because if one person infects 3 people,\n those people could each go on to infect 3 people\n then each person could infect 3 more.\n Just after 3 waves there would be 64 people infected.\n Now just think about how many people you interact with daily\n without social distancing"
};

int factChoice = 0;

public void mousePressed() {
  if (screen == 0) screen = 1;
  if (screen == 2) {
    reset();
    screen = 1;
  }
}

public void reset() {
  friends.clear();
  pacman.moveToPoint(600, 400);
  int rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
  rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
  rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
  timer = 0;
}

public void setup() {
  
  textAlign(CENTER);
  pacman.setRoundHitbox(12);
  for (Sprite s : walls) {
    s.setColor(0, 0, 255);
  }
  for (Sprite s : friends) {
    s.setRoundHitbox(12);
  }

  int rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
  rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
  rand = (int)random(0, spawnX.length);
  friends.add(new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25));
}

public void draw() {
  if (screen == 0) {
    background(255);
    textSize(50);
    fill(0);
    text("Welcome to Covid Pac Man!\nTry to avoid the virus spores for as long as \nyou can. More viruses spawn every second.\nGood luck!", 600, 100);
    textSize(14);
    text("Click anywhere to start", 600, 500);
  }

  if (screen == 1) {
    background(0);
    pacman.display();
    for (Sprite s : friends) {
      s.display();
    }
    //friend.displayHitbox();
    //pacman.displayHitbox();
    pacMovement();
    ghostHandler();
    wallStuff();
    moveQueueStuff();
    deathHandler();

    fill(0);
    rect(0, 0, 440, 800);
    rect(765, 0, 440, 800);
    rect(0, 0, 1200, 140);
    rect(0, 610, 1200, 140);
    fill(255);
    text("Score: " + timer/60, 50, 50);
    timer++;
    for(int i = 0; i < friends.size()-1; i++) {
      for(int s = i+1; s < friends.size(); s++) {
        if(friends.get(i).touchingSprite(friends.get(s))) {
          friends.remove(s);
        }
      }
    }

    //timer--;
    if (frameCount % 60 == 0) {

      int rand = (int)random(0, spawnX.length);
      friends.add((new Sprite("https://www.roswellpark.org/sites/default/files/2020-03/covid-19_virus.png", spawnX[rand], spawnY[rand], 25, 25)));
      //timer = 100;
    }
  }
  if (screen == 2) {
    background(212, 111, 104);
    textSize(50);
    text("Oh no you got infected!", 600, 150);
    textSize(30);
    text("Fact: " + facts[factChoice], 600, 250);
    textSize(14);
    text("Click anywhere to play again", 600, 600);
  }
}

public void deathHandler() {
  for (Sprite s : friends) {
    if (s.touchingSprite(pacman)) {
      screen = 2;
      factChoice = (int)random(0, facts.length);
    }
  }
}

public void ghostHandler() {

  // FRIEND 1

  for (Sprite s : friends) {
    if (canMoveUp2(s)) {
      if (upYes(s)) {
        s.moveY(-1.5f);
      }
    }

    if (canMoveDown2(s)) {
      if (downYes(s)) {
        s.moveY(1.5f);
      }
    }

    if (canMoveLeft2(s)) {
      if (leftYes(s)) {
        s.moveX(-1.5f);
      }
    }

    if (canMoveRight2(s)) {
      if (rightYes(s)) {
        s.moveX(1.5f);
      }
    }
  }
}

public boolean upYes(Sprite s) {
  Sprite thing1 = new Sprite(s.getX(), s.getY()-2, s.getW(), s.getH());
  if (thing1.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
public boolean downYes(Sprite s) {
  Sprite thing2 = new Sprite(s.getX(), s.getY()+2, s.getW(), s.getH());
  if (thing2.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
public boolean leftYes(Sprite s) {
  Sprite thing3 = new Sprite(s.getX()-2, s.getY(), s.getW(), s.getH());
  if (thing3.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
public boolean rightYes(Sprite s) {
  Sprite thing4 = new Sprite(s.getX()+2, s.getY(), s.getW(), s.getH());
  if (thing4.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}

public void moveQueueStuff() {
  if (moveQueue == 1 && canMoveUp(pacman)) {
    moveQueue = -1;
    stopMovement();
    up = true;
  }
  if (moveQueue == 2 && canMoveDown(pacman)) {
    moveQueue = -1;
    stopMovement();
    down = true;
  }
  if (moveQueue == 3 && canMoveLeft(pacman)) {
    moveQueue = -1;
    stopMovement();
    left = true;
  }
  if (moveQueue == 4 && canMoveRight(pacman)) {
    moveQueue = -1;
    stopMovement();
    right = true;
  }
}

public void wallStuff() {
  for (Sprite s : walls) {
    s.display();
    if (pacman.touchingSprite(s)) {
      if (up && s.getY() < pacman.getY()) {
        stopMovement(); 
        pacman.moveY(2.5f);
      }
      if (down && s.getY() > pacman.getY()) {
        stopMovement();
        pacman.moveY(-2.5f);
      }
      if (right && s.getX() > pacman.getX()) {
        stopMovement();
        pacman.moveX(-2.5f);
      }
      if (left && s.getX() < pacman.getX()) {
        stopMovement();
        pacman.moveX(2.5f);
      }
    }
  }
}

public void pacMovement() {
  if (up) pacman.moveY(-2);
  if (down) pacman.moveY(2);
  if (left) pacman.moveX(-2);
  if (right) pacman.moveX(2);

  if (pacman.getX() > 773) pacman.setX(432);
  if (pacman.getX() < 432) pacman.setX(773);
  if (pacman.getY() < 133) pacman.setY(617);
  if (pacman.getY() > 617) pacman.setY(133);
}

public boolean canMoveUp2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()-2, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveUp(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()-5, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveDown2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()+2, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveDown(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()+5, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveLeft2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()-2, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveLeft(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()-5, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveRight2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()+2, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public boolean canMoveRight(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()+5, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

public void keyPressed() {
  if (keyCode == UP) {
    if (canMoveUp(pacman)) {
      stopMovement();
      up = true;
    } else {
      moveQueue = 1;
    }
  }
  if (keyCode == DOWN) {
    if (canMoveDown(pacman)) {
      stopMovement();
      down = true;
    } else {
      moveQueue = 2;
    }
  }
  if (keyCode == LEFT) {
    if (canMoveLeft(pacman)) {
      stopMovement();
      left = true;
    } else {
      moveQueue = 3;
    }
  }
  if (keyCode == RIGHT) {
    if (canMoveRight(pacman)) {
      stopMovement();
      right = true;
    } else {
      moveQueue = 4;
    }
  }
}

public void stopMovement() {
  right = false;
  left = false;
  up = false;
  down = false;
  moveQueue = -1;
}
// The Sprite class represents a Sprite (Image) on the canvas with which
// you can move and rotate, with absolute or relative amounts or with
// reference to other points or sprites. The direction of the "front" of
// the image can be changed, as can the "hitbox" (collision-triggering
// area). The image can come from a URL, solid block of color, or another
// Sprite, and can be flipped or replaced as needed.
 
// In general, the fields/instance variables for the Sprite class
// starting with _ (underscore) should be regarded as "private" and
// should not be used outside of the class definition.
 
/* CONSTRUCTORS:
 
All constructors create Sprites at (x, y) with size (w, h)
Different constructors are available depending on image source,
  as well as a copy constructor.
 
  Sprite(String url, float x, float y, float w, float h)
    create a Sprite and load its image
    DON'T use this too many times - it will load a new image each time!
    Instead load an image with this once in setup(), then use the copy
      constructor any time you want another copy.
 
  Sprite(float x, float y, float w, float h)
    create a solid black Sprite
 
  Sprite(Sprite s)
    create a copy of Sprite s
 
*/
 
/* METHODS/FUNCTIONS:
 
BASIC FUNCTIONS
 
  void display()
    draw the Sprite with its current information
 
  void setSize(float w, float h)
 
  void setCoor(float x, float y)
    set both position coordinates at once
 
  void setX(float x)
 
  void setY(float y)
 
  void setImage(PImage img)
 
  float getX()
 
  float getY()
 
  float getW()
    get the width of the sprite
 
  float getH()
    get the height of the sprite
 
  PImage getImage()
 
TURNING/DIRECTION FUNCTIONS
 
  void turn(float degrees)
    turn the specified number of degrees
 
  void turnToPoint(float x, float y)
    turn to face the specified (x, y) location
 
  void turnToDir(float angle)
    turn to the specified angle
 
  void turnToSprite(Sprite s)
    turn to the specified Sprite s
 
  float getDir()
    get the direction (in degrees) the Sprite is facing
 
  void flip()
    flips Sprite image across its X axis
 
MOVEMENT FUNCTIONS
 
  void moveToPoint(float x, float y)
    move sprite to location (x, y)
 
  void moveToSprite(Sprite s)
    move sprite to location of Sprite s
 
  void moveX(float dx)
    move in the X direction by the specified amount
 
  void moveY(float dy)
    move in the Y direction by the specified amount
 
  void moveXY(float dx, float dy)
    move in both directions by the specified amounts
 
  void forward(float steps)
    move forward in the direction the sprite is facing
    by the specified number of steps (pixels)
 
  void sideStep(float steps)
    move "sideways" relative to the sprite's current facing
    negative steps goes "left", positive goes "right"
 
DISTANCE, LOCATION, AND TOUCHING FUNCTIONS
 
  float distTo(Sprite s)
    calculate the distance from this Sprite to Sprite s
 
  float distTo(float x, float y)
    calculate the distance from this Sprite to (x,y)
 
  boolean touchingSprite(Sprite s)
 
  boolean touchingPoint(float x, float y)
 
  boolean isInsideScreen()
 
HITBOX CONTROL
  (Hitbox: The boundaries of the Sprite for collision checks.)
 
  void displayHitbox()
    shows hitbox as simple red outline - very useful for debugging!
 
  void setRectHitbox(float w, float h)
    set rectangular hitbox of given size
    h is along the front-back axis
    w is along the side-side axis
 
  void resetRectHitbox()
    set rectangular hitbox of size based on image
 
  void setRoundHitbox(float r)
    set circular hitbox of given size
 
  void resetRoundHitbox()
    set circular hitbox based on image size
 
  void setHitboxCenter(float x, float y)
    recenter hitbox relative to center of image
 
  void resetHitboxCenter()
    recenter hitbox to exactly center of image
 
  void setHitboxPoints(PVector[] array)
    set hitbox by individual points, relative to center
      x position of points is along front-back axis
      y position of points is along side-side axis
 
*/
 
class Sprite {
  // do not modify these except through the provided methods
  PImage _img;
  float _w;
  float _h;
  float _x;
  float _y;
  PVector _rotVector; // for movement
  float _front = 0;   // angle of front relative to right of image
 
  PVector _hitboxCenter = new PVector();
  PVector[] _hitbox;
 
  boolean _flipped = false;
 
  // constructor to create a Sprite at (x, y) with size (w, h)
  // using the image provided by the url
  Sprite(String url, float x, float y, float w, float h) {
    _img = loadImage(url);
    _x = x;
    _y = y;
    _w = w;
    _h = h;
    _rotVector = new PVector(1, 0, 0);
    resetRectHitbox();
  }
 
  // constructor to create a Sprite at (x, y) with size (w, h)
  // with a solid black color. The color of this Sprite can
  // change using the setColor() function
  Sprite(float x, float y, float w, float h) {
    _img = createImage(1, 1, RGB);
    _x = x;
    _y = y;
    _w = w;
    _h = h;
    _rotVector = new PVector(1, 0, 0);
    resetRectHitbox();
  }
 
  // constructor to create a copy of Sprite s
  Sprite(Sprite s) {
    _img = s._img;
    _x = s._x;
    _y = s._y;
    _w = s._w;
    _h = s._h;
    _rotVector = new PVector(s._rotVector.x, s._rotVector.y, 0);
    _front = s._front;
    _hitboxCenter = new PVector(s._hitboxCenter.x, s._hitboxCenter.y);
    _hitbox = new PVector[s._hitbox.length];
    for (int i = 0; i < _hitbox.length; i++) {
        _hitbox[i] = new PVector(s._hitbox[i].x, s._hitbox[i].y);
    }
    _flipped = s._flipped;
  }
 
  // adjust the direction of the PImage of the Sprite
  // without changing the orientation of the Sprite
  public void frontAngle(float degrees) {
    float newFront = radians(degrees);
 
    // movement done from this direction from now on
    _rotVector.rotate(newFront - _front);
 
    _front = newFront;
  }
 
  // set rectangular hitbox of given size
  // h is along the front-back axis
  // w is along the perpendicular axis
  public void setRectHitbox(float w, float h) {
    _hitbox = new PVector[]{
      new PVector(-w/2, h/2),
      new PVector(-w/2, -h/2),
      new PVector(w/2, -h/2),
      new PVector(w/2, h/2)
    };
  }
 
  // set rectangular hitbox of size based on image
  public void resetRectHitbox() {
    setRectHitbox(_w, _h);
  }
 
  // set circular hitbox of given size
  public void setRoundHitbox(float r) {
    _hitbox = new PVector[]{new PVector(r, r*2)};
  }
 
  // set circular hitbox based on image size
  public void resetRoundHitbox() {
    setRoundHitbox((_w+_h)/4);
  }
 
  // recenter hitbox relative to center of image
  public void setHitboxCenter(float x, float y) {
    _hitboxCenter = new PVector(x, y);
  }
 
  // recenter hitbox to exactly center of image
  public void resetHitboxCenter() {
    _hitboxCenter = new PVector(0, 0);
  }
 
  public void setHitboxPoints(PVector[] array) {
    if (array.length > 0) {
      boolean valid = true;
      for (PVector pv : array) if (pv == null) valid = false;
 
      if (valid) _hitbox = array;
      else println("invalid hitbox: " + java.util.Arrays.toString(array));
    }
    else {
      println("hitbox must have 3+ points: " + java.util.Arrays.toString(array));
    }
  }
 
  // change the color of a Sprite created without an image
  public void setColor(float r, float g, float b) {
    int c = color(r, g, b);
    for(int x = 0; x < _img.width; x++) {
      for(int y = 0; y < _img.height; y++) {
        _img.set(x, y, c);
      }
    }
  }
 
  // flips Sprite image across its X axis
  public void flip() {
      _flipped = !_flipped;
  }
 
  // turn the specified number of degrees
  public void turn(float degrees) {
    _rotVector.rotate(radians(degrees));
  }
 
  // turn to the specified (x, y) location
  public void turnToPoint(float x, float y) {
    _rotVector.set(x - _x, y - _y, 0);
    _rotVector.setMag(1);
  }
 
  // turn to the specified angle
  public void turnToDir(float angle) {  
    float radian = radians(angle);
    _rotVector.set(cos(radian), sin(radian));
    _rotVector.setMag(1);
  }
 
  // turn to the specified Sprite s
  public void turnToSprite(Sprite s) {
    turnToPoint(s._x, s._y);
  }
 
  // move sprite to location (x, y)
  public void moveToPoint(float x, float y) {
    _x = x;
    _y = y;
  }
 
  // move sprite to location of Sprite s
  public void moveToSprite(Sprite s) {
    _x = s._x;
    _y = s._y;
  }
 
  // move in the X direction by the specified amount 
  public void moveX(float x) {
    _x += x;
  }
 
  // move in the Y direction by the specified amount 
  public void moveY(float y) {
    _y += y;
  }
 
  public void moveXY(float dx, float dy) {
      _x += dx;
      _y += dy;
  }
 
  // move forward in the direction the sprite is facing
  // by the specified number of steps (pixels)
  public void forward(float steps) {
    _x += _rotVector.x * steps;
    _y += _rotVector.y * steps;
  }
 
  // move 90 degree clockwise from the direction
  // the sprite is facing by the specified number of steps (pixels)
  public void sideStep(float steps) {
    _rotVector.rotate(PI / 2);
    _x += _rotVector.x * steps;
    _y += _rotVector.y * steps;
    _rotVector.rotate(-PI / 2);
  }
 
  // draw the Sprite. This function
  // should be called in the void draw() function
  public void display() {
    pushMatrix();
    pushStyle();
 
    translate(_x, _y);
    rotate(_rotVector.heading() - _front);
    if (_flipped) scale(-1, 1);
    imageMode(CENTER);
    image(_img, 0, 0, _w, _h);
 
    popStyle();
    popMatrix();
  }
 
  public void displayHitbox() {
    PVector cen = _getCenter();
 
    pushStyle();
    stroke(255, 0, 0);
    strokeWeight(5);
    noFill();
 
    if (_hitbox.length == 1) {
      ellipseMode(CENTER);
      ellipse(cen.x, cen.y, _hitbox[0].y, _hitbox[0].y);
    }
    else {
      PVector[] corners = _getPoints();
      for (int i = 0; i < corners.length; i++) {
        PVector a = corners[i];
        PVector b = corners[(i+1)%corners.length];
        line(a.x, a.y, b.x, b.y);
      }
    }
 
    line(cen.x, cen.y, cen.x + _rotVector.x * 20, cen.y + _rotVector.y * 20);
 
    fill(255,0,0);
    noStroke();
    ellipse(cen.x, cen.y, 15, 15);
 
    popStyle();
  }
 
  // set the size of the Sprite
  public void setSize(float w, float h) {
    _w = w;
    _h = h;
  }
 
  public void setCoor(float x, float y) {
    _x = x;
    _y = y;
  }
 
  // set the x coordinate
  public void setX(float x) {
    _x = x;
  }
 
  // set the y coordinate
  public void setY(float y) {
    _y = y;
  }
 
  // change the image of the Sprite
  public void setImage(PImage img) {
    _img = img;
  }
 
  // get the x coordinate of the sprite 
  public float getX() {
    return _x;
  }
 
  // get the y coordinate of the sprite
  public float getY() {
    return _y;
  }
 
  // get the width of the sprite
  public float getW() {
    return _w;
  }
 
  // get the height of the sprite
  public float getH() {
    return _h;
  }
 
  // get the image of the sprite
  public PImage getImage() {
    return _img;
  }
 
  // get the direction (in degrees) the Sprite is facing
  public float getDir() {
    return degrees(_rotVector.heading());
  }
 
  // calculate the distance from this Sprite to Sprite s
  public float distTo(Sprite s) {
    return dist(_x, _y, s._x, s._y);
  }
 
  public float distToPoint(float x, float y) {
    return dist(_x, _y, x, y);
  }
 
  // checks whether this Sprite is touching Sprite s
  public boolean touchingSprite(Sprite s) {
    if (s._hitbox.length == 1) {
      if (_hitbox.length == 1) {
        return PVector.dist(this._getCenter(), s._getCenter()) <= 
               this._hitbox[0].x + s._hitbox[0].x;
      }
      return _circPoly(s._getCenter(), s._hitbox[0].x, this._getPoints());
    }
    if (_hitbox.length == 1) {
      return _circPoly(this._getCenter(), this._hitbox[0].x, s._getPoints());
    }
 
    PVector[] s1Points = s._getPoints();
    PVector[] s2Points = this._getPoints();
 
    for(int i = 0; i < s1Points.length; i++) {
      PVector a = s1Points[i], b = s1Points[(i+1)%s1Points.length];
      for(int j = 0; j < s2Points.length; j++) {
        PVector c = s2Points[j], d = s2Points[(j+1)%s2Points.length];
 
        // sprites touch if ab crosses cd
        if(_clockwise(a, c, d) != _clockwise(b, c, d) &&  // a & b on different sides of cd, and
           _clockwise(a, b, c) != _clockwise(a, b, d)) {  // c & d on different sides of ab
          return true;
        }
      }
    }
 
    return _insidePts(s1Points,s2Points) || _insidePts(s2Points,s1Points);
  }
 
  //checks to see if this Sprite is fully inside another sprite
  public boolean insideSprite(Sprite s){
    if (s._hitbox.length == 1) {
      if (_hitbox.length == 1) {
        return PVector.dist(s._getCenter(),this._getCenter()) <
               s._hitbox[0].x - this._hitbox[0].x;
      }
      return _insideCirc(_getPoints(), s._getCenter(), s._hitbox[0].x);
    }
    if (s._hitbox.length == 1) {
      // TODO: check if center is in middle but NOT touching any side
      //   (will want to adapt existing _circPoly to separate side-touching
      //    code into individual method)
      return false;
    }
    return _insidePts(this._getPoints(), s._getPoints());
  }
 
  // checks whether this Sprite is touching the specified point
  public boolean touchingPoint(float x, float y) {
    if (_hitbox.length == 1) return dist(x,y,_hitboxCenter.x, _hitboxCenter.y) < _hitbox[0].x;
    return _ptPoly(new PVector(x,y), _getPoints());
  }
 
  // checks whether this Sprite's hitbox is at least partially inside the canvas
  // TODO: technically this returns true even if circular Sprite is just outside
  //   at the corners, and false if a tilted rectangular Sprite's edge crosses
  //   a corner with endpoints outside
  public boolean isInsideScreen() {
    if (_hitbox.length == 1) {
      float r = _hitbox[0].x;
      PVector c = _getCenter();
      return 0 <= c.x + r && c.x - r < width && 0 <= c.y + r && c.y - r < height;
    }
 
    PVector[] points = this._getPoints();
    for(PVector p : points) {
      if(0 <= p.x && p.x < width && 0 <= p.y && p.y < height) {
        return true;
      }
    }
    return false;
  }
 
  PVector out = new PVector(-10000, -10000); // any outside point
 
  // (pseudo-static) checks whether pt touches polygon
  public boolean _ptPoly(PVector pt, PVector[] poly) {  
    // count edges crossed by the line connecting the target point to "the outside"
    int count = 0;
 
    for(int i = 0; i < poly.length; i++) {
      PVector a = poly[i], b = poly[(i+1)%poly.length];  // edge points
      if(_clockwise(a, pt, out) != _clockwise(b, pt, out) &&  // a & b on different sides of line
         _clockwise(a, b, pt)   != _clockwise(a, b, out)) {   // tgt & out on diff sides of edge
        count++;
      }
    }
 
    return count % 2 == 1;
    // a convex poly would be crossed on one edge;
    //   concave could be crossed on any odd # of edges
  }
 
  // (pseudo-static) checks whether circle is touching polygon
  //   (including one inside the other)
  public boolean _circPoly(PVector center, float r, PVector[] poly) {
    // center is in polygon
    if (_ptPoly(center, poly)) return true;
    if (_insideCirc(poly, center, r)) return true;
 
    // circle encloses any corner
    for (PVector corner : poly) {
      if (dist(center.x, center.y, corner.x, corner.y) < r) return true;
    }
 
    // circle is adjacent and close enough to any side
    for (int i = 0; i < poly.length; i++) {
      if (_circSeg(center, r, poly[i], poly[(i+1)%poly.length])) return true;
    }
 
    return false;
  }
 
  // (pseudo-static) 
  // checks if circle touches segment AB from a perpendicular direction,
  //   but NOT from "beyond the ends"
  //   (this should be checked separately if desired)
  // aka, checks if center forms a perpendicular to any point on segment
  //   with length <= r 
  public boolean _circSeg(PVector center, float r, PVector a, PVector b) {
    PVector ab = PVector.sub(b,a);
    PVector abPerp = (new PVector(-ab.y, ab.x)).normalize().mult(r);
 
    PVector[] limits = new PVector[]{
      PVector.add(a,abPerp), // move perpendicular to the segment by
      PVector.sub(a,abPerp), // distance r from each of the endpoints,
      PVector.sub(b,abPerp), // forming a bounding rectangle
      PVector.add(b,abPerp)
    };
 
    return _ptPoly(center, limits);
  }
 
  // (pseudo-static) checks whether all inPts are completely within the outPts
  //   TODO: does not check whether edges between inPts are within outPts!
  public boolean _insidePts(PVector[] inPts, PVector[] outPts) {
 
    for(int i = 0; i < inPts.length; i++){
      // direction of angular relationship to any side must match
      //   direction of relationship to opposite side
      if(!_ptPoly(inPts[i], outPts)) return false;
    }
    return true;
  }
 
  // (pseudo-static) checks whether all inPts are completely within circle
  public boolean _insideCirc(PVector[] inPts, PVector center, float r) {
 
    for(int i = 0; i < inPts.length; i++){
      // direction of angular relationship to any side must match
      //   direction of relationship to opposite side
      if(PVector.dist(inPts[i],center) > r) return false;
    }
    return true;
  }
 
  // get hitbox absolute center based on image center, relative offset, rotation, and front
  public PVector _getCenter() {
    PVector cen = new PVector(_hitboxCenter.x, _hitboxCenter.y);
    cen.rotate(_rotVector.heading() - _front);
    cen.x += _x;
    cen.y += _y;
    return cen;
  }
 
  // get points representing rectangular hitbox
  public PVector[] _getPoints() {
    PVector cen = _getCenter();
 
    PVector[] points = new PVector[_hitbox.length];
    float angle = _rotVector.heading();
    for(int i = 0; i < _hitbox.length; i++) {
      points[i] = new PVector(_hitbox[i].x, _hitbox[i].y);
      points[i].rotate(angle);
      points[i].x += cen.x;
      points[i].y += cen.y;
    }
    return points;
  }
 
  // checks whether motion in AB turns clockwise to follow BC
  // i.e. which way is angle ABC concave?
  private boolean _clockwise(PVector A, PVector B, PVector C) {
    return (C.y-A.y) * (B.x-A.x) > (B.y-A.y) * (C.x-A.x);
  }
}
  public void settings() {  size(1200, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#74C8CF", "--stop-color=#cccccc", "Pacman" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
