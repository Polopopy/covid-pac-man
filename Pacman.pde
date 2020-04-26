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

void mousePressed() {
  if (screen == 0) screen = 1;
  if (screen == 2) {
    reset();
    screen = 1;
  }
}

void reset() {
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

void setup() {
  size(1200, 800);
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

void draw() {
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

void deathHandler() {
  for (Sprite s : friends) {
    if (s.touchingSprite(pacman)) {
      screen = 2;
      factChoice = (int)random(0, facts.length);
    }
  }
}

void ghostHandler() {

  // FRIEND 1

  for (Sprite s : friends) {
    if (canMoveUp2(s)) {
      if (upYes(s)) {
        s.moveY(-1.5);
      }
    }

    if (canMoveDown2(s)) {
      if (downYes(s)) {
        s.moveY(1.5);
      }
    }

    if (canMoveLeft2(s)) {
      if (leftYes(s)) {
        s.moveX(-1.5);
      }
    }

    if (canMoveRight2(s)) {
      if (rightYes(s)) {
        s.moveX(1.5);
      }
    }
  }
}

boolean upYes(Sprite s) {
  Sprite thing1 = new Sprite(s.getX(), s.getY()-2, s.getW(), s.getH());
  if (thing1.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
boolean downYes(Sprite s) {
  Sprite thing2 = new Sprite(s.getX(), s.getY()+2, s.getW(), s.getH());
  if (thing2.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
boolean leftYes(Sprite s) {
  Sprite thing3 = new Sprite(s.getX()-2, s.getY(), s.getW(), s.getH());
  if (thing3.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}
boolean rightYes(Sprite s) {
  Sprite thing4 = new Sprite(s.getX()+2, s.getY(), s.getW(), s.getH());
  if (thing4.distTo(pacman) < s.distTo(pacman)) return true;
  return false;
}

void moveQueueStuff() {
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

void wallStuff() {
  for (Sprite s : walls) {
    s.display();
    if (pacman.touchingSprite(s)) {
      if (up && s.getY() < pacman.getY()) {
        stopMovement(); 
        pacman.moveY(2.5);
      }
      if (down && s.getY() > pacman.getY()) {
        stopMovement();
        pacman.moveY(-2.5);
      }
      if (right && s.getX() > pacman.getX()) {
        stopMovement();
        pacman.moveX(-2.5);
      }
      if (left && s.getX() < pacman.getX()) {
        stopMovement();
        pacman.moveX(2.5);
      }
    }
  }
}

void pacMovement() {
  if (up) pacman.moveY(-2);
  if (down) pacman.moveY(2);
  if (left) pacman.moveX(-2);
  if (right) pacman.moveX(2);

  if (pacman.getX() > 773) pacman.setX(432);
  if (pacman.getX() < 432) pacman.setX(773);
  if (pacman.getY() < 133) pacman.setY(617);
  if (pacman.getY() > 617) pacman.setY(133);
}

boolean canMoveUp2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()-2, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveUp(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()-5, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveDown2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()+2, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveDown(Sprite thing) {
  Sprite helper = new Sprite(thing.getX(), thing.getY()+5, thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveLeft2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()-2, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveLeft(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()-5, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveRight2(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()+2, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

boolean canMoveRight(Sprite thing) {
  Sprite helper = new Sprite(thing.getX()+5, thing.getY(), thing.getW(), thing.getH());
  for (Sprite s : walls) {
    if (helper.touchingSprite(s)) {
      return false;
    }
  }
  return true;
}

void keyPressed() {
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

void stopMovement() {
  right = false;
  left = false;
  up = false;
  down = false;
  moveQueue = -1;
}
