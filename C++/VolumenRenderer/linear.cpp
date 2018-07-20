// /*
#define _USE_MATH_DEFINES
#include <cmath>
#include <stdlib.h>
    
    double f(double x, double y, double z) {
        return 2*x+3*y+z;
    }

	double f(double x[]) {
        return f(x[0], x[1], x[2]);
    }

float lerp(float x, float x1, float x2, float q00, float q01) {
    return ((x2 - x) / (x2 - x1)) * q00 + ((x - x1) / (x2 - x1)) * q01;
}
    
float triLerp(float x, float y, float z,
                            float q000, float q001, float q010,
                            float q011, float q100, float q101,
                            float q110, float q111,
                            float x1, float x2, float y1, float y2, float z1, float z2) {
    float x00 = lerp(x, x1, x2, q000, q100);
    float x10 = lerp(x, x1, x2, q010, q110);
    float x01 = lerp(x, x1, x2, q001, q101);
    float x11 = lerp(x, x1, x2, q011, q111);
    float r0 = lerp(y, y1, y2, x00, x01);
    float r1 = lerp(y, y1, y2, x10, x11);
    
    return lerp(z, z1, z2, r0, r1);
}


// Shepard.    
double* sub(double p[], double q[]) {
    double* new_p = p;
    for(int i = 0; i < 3; i++) {
        new_p[i] -= q[i];
    }
    return new_p;
}
    
double euklide(double p[], double q[]) {
    double* new_p = sub(p,q);
    double sum = 0;
    for(int i = 0; i < 3; i++) {
        sum += std::pow(new_p[i],2);
    }
    return sqrt(sum);
}

int sumBot;
            
double d(double p[], double p_i[]) {
    double top = std::pow(abs(euklide(p,p_i)), -2);
    double bot = sumBot;
    return top / bot;
}

            
double interpolate(double s_i[][3], double p[]) {
    double sumBot = 0;
    for (int i = 0; i < 8 ;i++) {
        sumBot += std::pow(abs(euklide(p,s_i[i])), -2);
    }

	double sum = 0;
	for (int i = 0; i < 3;) {
		sum += (d(p, s_i[i])*f(s_i[i]));
	}
	return sum;
}
// */

