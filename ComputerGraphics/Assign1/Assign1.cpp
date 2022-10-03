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

// 윈도우의 렌더링을 담당하는 콜백 함수
void Render()
{
	// 색상 버퍼를 지울 색을 지정한다.
	glClearColor(1.0, 1.0, 1.0, 1.0);// 0~1.0

	// 색상 버퍼를 지운다.
	glClear(GL_COLOR_BUFFER_BIT);

	// 관측 영역을 지정한다.
	glMatrixMode(GL_PROJECTION); // 현재 행렬을 투영행렬로 선택한다.
	glLoadIdentity(); //선택된 투영행렬을 단위행렬로 초기화한다.
	gluOrtho2D(-gx, gx, -gy, gy); //좌표계의 영역을 지정한다.

	glMatrixMode(GL_MODELVIEW);

	WhiteBoard(0, 5, 1);

	Professor(-5, 5, 1);

	Students4(-8, -5, 1);
	Students4(0, -5, 1);
	Students4(8, -5, 1);

	
	// 렌더링 종료
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

	//강제로  Render() 함수를 호출한다.
	glutPostRedisplay();
}

int main(int argc, char **argv)
{
	//glut 초기화(무조건 똑같이...)
	glutInit(&argc, argv);

	// 생성할 크기의 윈도우 지정
	glutInitWindowSize(Width, Height);

	// 윈도우의 디스플레이모드를 RGB로 설정
	glutInitDisplayMode(GLUT_RGB);

	// 윈도우를 생성한다.
	glutCreateWindow("Assignment1");

	// 윈도우 화면이 바뀔 때, 자동으로 호출되는 콜백 함수
	glutDisplayFunc(Render);

	// 윈도우의 크기가 변경될 때, 자동으로 호출되는 콜백 함수
	glutReshapeFunc(Reshape);

	glutKeyboardFunc(Keyboard);

	// 메시지 루프로 진입
	glutMainLoop();
	/*
	while (true)
	{
		// 이벤트가 발생하면 해당 콜백 함수를 호출...
	}
	*/
	return 0;
}