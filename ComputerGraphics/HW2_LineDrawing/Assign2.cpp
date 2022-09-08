#include <stdio.h>
#include <stdlib.h>
#include <gl/freeglut.h>
#include <string.h>
#include <vector>

using namespace std;

class Line {
public:
	float x0, y0 , x1, y1;
	float r=1, g=0, b=0;
	float width = 1.0;
	short pattern = 0xffff;
};

vector<Line *> lineVec;
Line curLine;

bool isDrawing = false;

int Width = 800, Height = 600;

void Display();
void Reshape(int w, int h);
void Close();
void Mouse(int, int, int, int);
void Motion(int, int);
void PassiveMotion(int, int);
void Keyboard(unsigned char, int, int);

void ColorMenu(int id);
void WidthMenu(int id);
void PatternMenu(int id);
void CreateMenu(int id);

int main(int argc, char **argv)
{;
	glutInit(&argc, argv);
	glutInitWindowSize(Width, Height);

	glutInitDisplayMode(GLUT_RGB);

	glutCreateWindow("Assignment 2 Line Drawer");

	glutDisplayFunc(Display);
	glutReshapeFunc(Reshape);
	glutCloseFunc(Close);
	glutMouseFunc(Mouse);
	glutMotionFunc(Motion);
	glutKeyboardFunc(Keyboard);
	glutPassiveMotionFunc(PassiveMotion);

	GLint ColorMenuId = glutCreateMenu(ColorMenu);
	glutAddMenuEntry("Red", 0);
	glutAddMenuEntry("Green", 1);
	glutAddMenuEntry("Blue", 2);

	GLint WidthMenuId = glutCreateMenu(WidthMenu);
	glutAddMenuEntry("1.0", 0);
	glutAddMenuEntry("3.0", 1);
	glutAddMenuEntry("5.0", 2);
	
	GLint PatternMenuId = glutCreateMenu(PatternMenu);
	glutAddMenuEntry("Solid", 0);
	glutAddMenuEntry("Dotted", 1);

	GLint CreateMenuId = glutCreateMenu(CreateMenu);
	glutAddSubMenu("Color", ColorMenuId);
	glutAddSubMenu("Width", WidthMenuId);
	glutAddSubMenu("Pattern", PatternMenuId);
	glutAddMenuEntry("Exit", 3);

	glutAttachMenu(GLUT_RIGHT_BUTTON);

	glutMainLoop();
	return 0;
}

void Display()
{
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, Width, 0, Height);

	glMatrixMode(GL_MODELVIEW);

	if (isDrawing == true)
	{
		glEnable(GL_LINE_STIPPLE);
		glColor3f(curLine.r, curLine.g, curLine.b);
		glLineWidth(curLine.width);
		glLineStipple(1, curLine.pattern);
		glBegin(GL_LINES);
		glVertex2f(curLine.x0, curLine.y0);
		glVertex2f(curLine.x1, curLine.y1);
		glEnd();
		glDisable(GL_LINE_STIPPLE);
	}
	
	for (int i = 0; i < lineVec.size(); i++)
	{
		glEnable(GL_LINE_STIPPLE);
		glColor3f(lineVec[i]->r, lineVec[i]->g, lineVec[i]->b);
		glLineWidth(lineVec[i]->width);
		glLineStipple(1, lineVec[i]->pattern);
		glBegin(GL_LINES);
		glVertex2f(lineVec[i]->x0, lineVec[i]->y0);
		glVertex2f(lineVec[i]->x1, lineVec[i]->y1);
		glEnd();
		glDisable(GL_LINE_STIPPLE);
	}

	glFinish();
}

void Reshape(int w, int h)
{
	glViewport(0, -(Height-h), Width, Height);
}

void Close()
{
	//프로그램 종료에 따른 후처리...
}

void Mouse(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON)
	{
		if (state == GLUT_DOWN)
		{
			curLine.x0 = x; curLine.y0 = Height - y;
			curLine.x1 = x; curLine.y1 = Height - y;
			isDrawing = true;
		}
		
		if (state == GLUT_UP)
		{
			if (isDrawing == true)
			{
				Line *newLine = new Line(curLine);
				lineVec.push_back(newLine);
			}
			isDrawing = false;
		}
	}
}

void Motion(int x, int y)
{
	if (isDrawing == true)
	{
		curLine.x1 = x; curLine.y1 = Height - y;
		
	}
	else if (isDrawing == false)
	{
		curLine.x0 = x; curLine.y0= Height - y;
		curLine.x1 = x; curLine.y1 = Height - y;
	}
	glutPostRedisplay();
}
void PassiveMotion(int x, int y)
{
	//printf("[%d,%d]\n", x, y);
}

void ColorMenu(int id)
{
	if (id == 0)
	{
		curLine.r = 1;
		curLine.g = 0;
		curLine.b = 0;
	}
	if (id == 1)
	{
		curLine.r = 0;
		curLine.g = 1;
		curLine.b = 0;
	}
	if (id == 2)
	{
		curLine.r = 0;
		curLine.g = 0;
		curLine.b = 1;
	}
}

void WidthMenu(int id)
{
	if (id == 0)
	{
		curLine.width = 1.0f;
	}
	if (id == 1)
	{
		curLine.width = 3.0f;
	}
	if (id == 2)
	{
		curLine.width = 5.0f;
	}
}

void PatternMenu(int id)
{
	if (id == 0)
	{
		curLine.pattern = 0xffff;
	}
	if (id == 1)
	{
		curLine.pattern = 0xf0f0;
	}
}

void CreateMenu(int id)
{
	if (id == 3) exit(0);
}

void Keyboard(unsigned char key, int x, int y)
{
	if (key == 27)
	{
		curLine.x0 = x;
		curLine.y0 = y;
		curLine.x1 = x;
		curLine.y1 = y;
		isDrawing = false;
		glutPostRedisplay();
	}
}