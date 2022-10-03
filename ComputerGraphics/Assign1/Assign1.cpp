#include <iostream>
#include <cstdio>
#include <GL\freeglut.h>
#define _USE_MATH_DEFINES
#include <math.h>

using namespace std;

int Width = 600;
int Height = 600;

double gx = 10.0;
double gy = 10.0;

double angle = 0.0;

void Tri(GLfloat topx, GLfloat topy,
	GLfloat leftx, GLfloat lefty, GLfloat rightx, GLfloat righty)
{
	glBegin(GL_TRIANGLES);
	{
		glVertex2d(topx, topy);
		glVertex2d(leftx, lefty);
		glVertex2d(rightx, righty);
	}
	glEnd();
}

void Squad(
	GLfloat topLeftx, GLfloat topLefty, GLfloat topRightx, GLfloat topRighty,
	GLfloat botLeftx, GLfloat botLefty, GLfloat botRightx, GLfloat botRighty
)
{
	glBegin(GL_QUAD_STRIP);
	{
		glVertex2d(topLeftx, topLefty); glVertex2d(topRightx, topRighty);
		glVertex2d(botLeftx, botLefty); glVertex2d(botRightx, botRighty);
	}
	glEnd();
}

void Rect(GLfloat cx, GLfloat cy, GLfloat width, GLfloat height)
{
	Squad(
		cx - width / 2, cy + height / 2, cx + width / 2, cy + height / 2,
		cx - width / 2, cy - height / 2, cx + width / 2, cy - height / 2
	);
}

void SubEllipse(
	GLfloat cx, GLfloat cy,
	GLfloat xr, GLfloat yr,
	GLfloat angleStart, GLfloat angleEnd
)
{
	GLfloat theta;
	glBegin(GL_POLYGON);
	{
		for (theta = angleStart * 2 * M_PI / 360; theta <= angleEnd*2*M_PI/360; theta+= 2 * M_PI / 360)
		{
			glVertex2d(cx + xr * cos(theta), cy + yr * sin(theta));
		}
	}
	glEnd();
}

void SubCircle(
	GLfloat cx, GLfloat cy, GLfloat radius,
	GLfloat angleStart, GLfloat angleEnd
)
{
	SubEllipse(cx, cy, radius, radius, angleStart, angleEnd);
}

void HeadBack(GLfloat cx, GLfloat cy, GLfloat size)
{
	glColor3f(1, 0.8, 0.5);
	SubCircle(cx, cy, size, 0, 360);
	glColor3f(0, 0, 0);
	SubCircle(cx, cy, size + 0.1*size, 0, 180);
}

void Chair(GLfloat cx, GLfloat cy, GLfloat size)
{
	glColor3f(0, 0, 0);
	Rect(cx - 0.4*size, cy - 1*size, size*0.2, size*0.5);
	Rect(cx + 0.4*size, cy - 1*size, size*0.2, size*0.5);
	glColor3f( 0.3, 0.3, 1);
	Rect(cx, cy, size, size*1.5);
	
}

void Student(GLfloat cx,GLfloat cy, GLfloat size)
{
	HeadBack(cx, cy + 1*size, size);
	Chair(cx, cy-0.5*size , size*1.3*size);
}

void Students4(GLfloat cx, GLfloat cy, GLfloat size)
{
	Student(cx - 1.2*size, cy + 2*size, size);
	Student(cx + 1.2*size, cy + 2*size, size);
	Student(cx - 1.2*size, cy - 2*size, size);
	Student(cx + 1.2*size, cy - 2*size, size);
}

void WhiteBoard(GLfloat cx, GLfloat cy, GLfloat size)
{
	glColor3f(0, 0, 0);
	Rect(cx, cy, size * 19, size * 8);
	glColor3f(1, 1, 1);
	Rect(cx, cy, size * 18.5, size * 7.5);
	glColor3f(0, 0, 0);
	Rect(cx - 2.8, cy, size * 2, size * 5);
	glColor3f(1, 1, 1);
	Rect(cx - 2.7, cy, size * 1.8, size * 4.8);
	glColor3f(0, 0, 0);
	Rect(cx + 2.8, cy, size * 2, size * 5);
	glColor3f(1, 1, 1);
	Rect(cx + 2.7, cy, size * 1.8, size * 4.8);
	glColor3f(0, 0, 0);
	int i, j;
	for (i = -2; i <= 2; i += 2)
	{
		for (j = -2; j <= 2; j += 2)
		{
			Rect(cx + i, cy + j, 0.5, 0.5);
		}
	}
	Rect(cx + 7, cy, 4 * size, 0.1*size);
	Rect(cx + 7, cy, 0.1 * size, 4*size);
	Tri(cx + 7.5*size, cy + 1.5 * size, cx + 7 * size, cy, cx + 8.5 * size, cy + 0.5*size);
}

void Glass(GLfloat cx, GLfloat cy, GLfloat s)
{
	glColor3f(0, 0, 0);
	Rect(cx, cy + 0.1, s*0.2, s*0.1);
	glColor3f(0.7, 0.7, 0.7);
	SubCircle(cx - 0.5, cy, s*0.4, 0, 360);
	SubCircle(cx + 0.5, cy, s*0.4, 0, 360);
}

void Head(GLfloat cx, GLfloat cy, GLfloat size)
{
	glColor3f(1, 0.8, 0.5);
	SubEllipse(cx, cy, size*0.9, size*1.1, 0, 360);
	glColor3f(0, 0, 0);
	SubEllipse(cx, cy + 0.2*size, size*1.1, size*0.9, 10, 170);
	glColor3f(0.8, 0.1, 0.1);
	SubEllipse(cx, cy - 0.5*size, size*0.3, size*0.2, 0, 360);
	Glass(cx, cy+0.1, size);
}

void Body(GLfloat cx, GLfloat cy, GLfloat s)
{
	glColor3f(0, 0, 0);
	Rect(cx + 2.9 * s, cy + 1.9*s, 0.2 * s, 1.3 * s);
	glColor3f(1, 0.8, 0.5);
	Rect(cx + 3 * s, cy + 1.5*s, 1 * s, 0.7 * s);
	glColor3f(0.4, 0.3, 0.7);
	Rect(cx, cy-1.5*s, s*2, s*3);
	Rect(cx + 2*s, cy-0.9*s, 2.5*s, 1*s);
	Rect(cx + 3 * s, cy - 0.15*s, 1*s, 2.5 * s);
	Rect(cx - 1*s, cy - 0.6*s, 2.3*s, 1*s);
	Rect(cx - 2 * s, cy - 1.2*s, 1*s, 2 * s);
	glColor3f(1, 0.8, 0.5);
	Rect(cx - 2 * s, cy - 2*s, 1 * s, 0.7 * s);
}

void Leg(GLfloat cx, GLfloat cy, GLfloat s)
{
	glColor3f(0.5, 0.2, 0);
	SubEllipse(cx - 1.3 * s, cy - 3 * s, 0.6*s, 0.3*s, -30, 210);
	SubEllipse(cx + 1.3 * s, cy - 3 * s, 0.6*s, 0.3*s, -30, 210);
	glColor3f(0.1, 0, 0.5);
	Tri(cx, cy + 2 * s, cx - 1.5 * s, cy - 3 * s, cx + 1.5 * s, cy - 3 * s);
	glColor3f(1, 1, 1);
	Tri(cx, cy - 1*s, cx - 0.5 * s, cy - 3 * s, cx + 0.5 * s, cy - 3 * s);
}

void Professor(GLfloat cx, GLfloat cy, GLfloat s)
{
	Head(cx, cy+0.5*s, s);
	Leg(cx, cy - 3.5 * s, s);
	Body(cx, cy - 0.5*s, s);
}

// �������� �������� ����ϴ� �ݹ� �Լ�
void Render()
{
	// ���� ���۸� ���� ���� �����Ѵ�.
	glClearColor(1.0, 1.0, 1.0, 1.0);// 0~1.0

	// ���� ���۸� �����.
	glClear(GL_COLOR_BUFFER_BIT);

	// ���� ������ �����Ѵ�.
	glMatrixMode(GL_PROJECTION); // ���� ����� ������ķ� �����Ѵ�.
	glLoadIdentity(); //���õ� ��������� ������ķ� �ʱ�ȭ�Ѵ�.
	gluOrtho2D(-gx, gx, -gy, gy); //��ǥ���� ������ �����Ѵ�.

	glMatrixMode(GL_MODELVIEW);

	WhiteBoard(0, 5, 1);

	Professor(-5, 5, 1);

	Students4(-8, -5, 1);
	Students4(0, -5, 1);
	Students4(8, -5, 1);

	
	// ������ ����
	glFinish();
}

void Reshape(int width, int height)
{
	glViewport(0, 0, width , height);
}

void Keyboard(unsigned char key, int x, int y)
{
	/*
	if (key == '1')
	{
		gx *= 2.0;
		gy *= 2.0;
	}
	*/

	//������  Render() �Լ��� ȣ���Ѵ�.
	glutPostRedisplay();
}

int main(int argc, char **argv)
{
	//glut �ʱ�ȭ(������ �Ȱ���...)
	glutInit(&argc, argv);

	// ������ ũ���� ������ ����
	glutInitWindowSize(Width, Height);

	// �������� ���÷��̸�带 RGB�� ����
	glutInitDisplayMode(GLUT_RGB);

	// �����츦 �����Ѵ�.
	glutCreateWindow("Assignment1");

	// ������ ȭ���� �ٲ� ��, �ڵ����� ȣ��Ǵ� �ݹ� �Լ�
	glutDisplayFunc(Render);

	// �������� ũ�Ⱑ ����� ��, �ڵ����� ȣ��Ǵ� �ݹ� �Լ�
	glutReshapeFunc(Reshape);

	glutKeyboardFunc(Keyboard);

	// �޽��� ������ ����
	glutMainLoop();
	/*
	while (true)
	{
		// �̺�Ʈ�� �߻��ϸ� �ش� �ݹ� �Լ��� ȣ��...
	}
	*/
	return 0;
}