#define _USE_MATH_DEFINES
#include <math.h>

//#include <GLUT/glut.h>
#include <GLUT/GLUT.h>
#include <stdlib.h>
#include <stdio.h>

#include "linear.h"
#include "color.h"

#include "needed/volren/ddsbase.h"
// In the Tutorium this was called a Bug, but this Extern variable just has to be defined.
void (*vrerrorhandler)(const char *file,int line,int fatal,const char *msg);


#define textureWidth 512
#define textureHeight 512
static GLubyte texture[textureHeight][textureWidth][4];

static GLuint texName;

/** Moving **/
float x_pos = 0;
float y_pos = 0;
float z_pos = 0;
float theta = M_PI/6;
float phi  = M_PI/2;
float R = 9;

void changeSize(int w, int h)
{
	if(h == 0)
		h = 1;
	float ratio = 1.0* w / h;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, w, h);
	gluPerspective(90,ratio,1,1000);
}

/** END Moving **/

int planeMode = 2;
float planePosition = 0;
int resolution = 1;

unsigned int width;
unsigned int height;
unsigned int depth;
unsigned int components;
float scalex;
float scaley;
float scalez;

unsigned char* volume;

int imageScaleX;
int imageScaleY;
int imageScaleZ;

void loadDataX() {
	
	imageScaleX = width/5.0;
	imageScaleY = textureHeight/height;
	imageScaleZ = ceil(textureHeight/(depth*1.0));

	int i, j, c;

	int x;
	int y;
	int z;
	for (i = 0; i < textureWidth; i++) {
		if (i % (imageScaleZ+resolution-1) == 0) {
			z = floor(i/imageScaleZ/scalez);
		}
        for (j = 0; j < textureHeight; j++) {
			if (j % (imageScaleY+resolution-1) == 0) {
				y = floor(j/imageScaleY/scaley);
			}
			x = floor((imageScaleX * (planePosition + 2.5)) / scalex);
			
			int point = (x+y*width+z*width*height)*components;
            c = volume[point];
            texture[i][j][0] = (GLubyte) c;
            texture[i][j][1] = (GLubyte) c;
            texture[i][j][2] = (GLubyte) c;
            texture[i][j][3] = (GLubyte) 255;
        }
    }
}

void loadDataY() {
	
	imageScaleX = textureWidth/width;
	imageScaleY = height/5.0;
	imageScaleZ = ceil(textureHeight/(depth*1.0));

	int i, j, c;

	int x;
	int y;
	int z;
	for (i = 0; i < textureWidth; i++) {
		if (i % (imageScaleZ+resolution-1) == 0) {
			z = floor(i/imageScaleZ/scalez);
		}
        for (j = 0; j < textureHeight; j++) {
			if (j % (imageScaleX+resolution-1) == 0) {
				x = floor(j/imageScaleX/scalex);
			}
			y = floor((imageScaleY * (planePosition + 2.5)) / scaley);
			
			int point = (x+y*width+z*width*height)*components;
            c = volume[point];
            texture[i][j][0] = (GLubyte) c;
            texture[i][j][1] = (GLubyte) c;
            texture[i][j][2] = (GLubyte) c;
            texture[i][j][3] = (GLubyte) 255;
        }
    }
}

void loadDataZ() {
	imageScaleX = textureWidth/width;
	imageScaleY = textureHeight/height;
	imageScaleZ = (depth/5.);
	int i, j, c;

	int x;
	int y;
	int z;
	for (i = 0; i < textureWidth; i++) {
		if (i % (imageScaleY+resolution-1) == 0) {
			y = floor(i/imageScaleY/scaley);
		}
        for (j = 0; j < textureHeight; j++) {
			if (j % (imageScaleX+resolution-1) == 0) {
				x = floor(j/imageScaleX/scalex);
			}
			z = floor((imageScaleZ * (planePosition + 2.5)) / scalez);
			
			int point = (x+y*width+z*width*height)*components;
            c = volume[point];
            texture[i][j][0] = (GLubyte) c;
            texture[i][j][1] = (GLubyte) c;
            texture[i][j][2] = (GLubyte) c;
            texture[i][j][3] = (GLubyte) 255;
        }
    }
}


void loadData() {
	switch(planeMode) {
		case 0:
			loadDataX();
			break;
		case 1:
			loadDataY();
			break;
		case 2:
			loadDataZ();
			break;
	}
}

void maketexture(void)
{
	loadData();
}

void invalidateTex() {
	maketexture();
    glGenTextures(1, &texName);
    glBindTexture(GL_TEXTURE_2D, texName);
    
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); 
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); 
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); 
    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureWidth,
                 textureHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                 texture);
}

void init(void)
{
	volume = readPVMvolume("needed/Baby.pvm", &width,&height,&depth,&components,&scalex,&scaley,&scalez);

	int length = width*height*depth*components;
	initColor(volume,length);
	
	imageScaleX = textureWidth/width;
	imageScaleY = textureHeight/height;
	imageScaleZ = (depth/5.);
	
	printf("Resolution: %d, %d, %d \n" , width, height, depth);
	printf("Else: %d, %f, %f, %f \n" , components, scalex, scaley, scalez);
	printf("Scale: %d, %d, %d \n" , imageScaleX, imageScaleY, imageScaleZ);

    glClearColor (1.0, 0.0, 0.0, 0.0);
    glShadeModel(GL_FLAT);
    glEnable(GL_DEPTH_TEST);
    
	glMatrixMode(GL_PROJECTION);
	gluPerspective(90, 1, 1, 1000);

	invalidateTex();
}

void display(void)
{
	/** Looking **/
	x_pos = R * cos(phi)*cos(theta);
	y_pos = R * sin(theta);
	z_pos = R * sin(phi)*cos(theta);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(1, 0, 0, 0);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glPushMatrix();
	if (cos(theta)>=0) gluLookAt(x_pos, y_pos, z_pos, 0, 0, 0, 0, 1, 0);
	else gluLookAt(x_pos, y_pos, z_pos, 0, 0, 0, 0, -1, 0);
	/** END Looking **/
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    glEnable(GL_TEXTURE_2D);
    glBegin(GL_QUADS);
	switch(planeMode) {
		case 0: 
			glTexCoord2f(0.0, 0.0); glVertex3f( planePosition,-2.5, -2.5);
			glTexCoord2f(0.0, 1.0); glVertex3f( planePosition,-2.5, 2.5 );
			glTexCoord2f(1.0, 1.0); glVertex3f( planePosition,2.5 , 2.5 );
			glTexCoord2f(1.0, 0.0); glVertex3f( planePosition,2.5 , -2.5);
			break;
		case 1: 
			glTexCoord2f(0.0, 0.0); glVertex3f(-2.5, planePosition, -2.5);
			glTexCoord2f(0.0, 1.0); glVertex3f(-2.5, planePosition, 2.5 );
			glTexCoord2f(1.0, 1.0); glVertex3f(2.5 , planePosition, 2.5 );
			glTexCoord2f(1.0, 0.0); glVertex3f(2.5 , planePosition, -2.5);
			break;
		case 2:
			glTexCoord2f(0.0, 0.0); glVertex3f(-2.5, -2.5, planePosition);
			glTexCoord2f(0.0, 1.0); glVertex3f(-2.5, 2.5, planePosition);
			glTexCoord2f(1.0, 1.0); glVertex3f(2.5, 2.5, planePosition);
			glTexCoord2f(1.0, 0.0); glVertex3f(2.5, -2.5, planePosition);
			break;
	}
	glEnd();
    glFlush();
    glDisable(GL_TEXTURE_2D);

	glColor3f(1.0f, 1.0f, 0.5f);
	glutWireCube(5.0);
    glFlush();

	glPopMatrix();
	glutSwapBuffers();
}


void reshape(int w, int h)
{
    glViewport(0, 0, (GLsizei) w, (GLsizei) h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(60.0, (GLfloat) w/(GLfloat) h, 1.0, 30.0);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0.0, 0.0, -3.6);
}

void keyboard (unsigned char key, int x, int y)
{
    switch (key) {
		case 'w': theta += 0.1;break;
		case 's': theta -= 0.1; break;
		case 'a': phi += 0.1; break;
		case 'd': phi -= 0.1; break;
		case 't': if (R >= 8) R -= 0.5; break;
		case 'g': if (R<20) R += 0.5; break;
		case '+': 
			if (planePosition <= 2.4) 
				planePosition += 0.1;
			invalidateTex();
			break;
		case '-': 
			if (planePosition >= -2.4)
				planePosition -= 0.1;
			invalidateTex();
			break;
		case 'f':
			planeMode = (planeMode+1)%3;
			invalidateTex();
			break;
		case 'r': 
			resolution = (resolution)%6+1; 
			invalidateTex();
			break;
        case 27:
            exit(0);
            break;
        default:
            break;
    }
	glutPostRedisplay();
}

int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB | GLUT_DEPTH);
	int width = 700;
	int height = 700;
    glutInitWindowSize(width, height);
	glutInitWindowPosition((glutGet(GLUT_SCREEN_WIDTH)-width)/2,
                       (glutGet(GLUT_SCREEN_HEIGHT)-height)/2);
    glutCreateWindow(argv[0]);
    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMainLoop();
    return 0; 
}