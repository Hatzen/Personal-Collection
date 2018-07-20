#include "color.h"

#include <math.h>       /* fmod */
#include <stdlib.h>

int max;
int min;

void initColor(unsigned char* values, int length) {
	for (int m = 0 ; m < length ; m++)
    {
        if (values[m] > max)
            max = values[m];
    }
	min = 9999;
	for (int m = 0 ; m < length ; m++)
    {
        if (values[m] < min)
            min = values[m];
    }
}

double* hsv2rgb(double hsv[3])
{
    float h = hsv[0] * 6.; /* H in 0°=0 ... 1=360° */
    float s = hsv[1];
    float v = hsv[2];
    float c = v * s;

	float cx[2] = {v*s, c * ( 1 - abs(fmod(h, 2.f)-1.) )};

	double rgb[3] = {0., 0., 0.};
    if( h < 1. ) {
		rgb[0] = cx[0];
		rgb[1] = cx[1];
    } else if( h < 2. ) {
		rgb[1] = cx[0];
		rgb[0] = cx[1];
    } else if( h < 3. ) {
		rgb[1] = cx[0];
		rgb[2] = cx[1];
    } else if( h < 4. ) {
		rgb[2] = cx[0];
		rgb[1] = cx[1];
    } else if( h < 5. ) {
		rgb[2] = cx[0];
		rgb[0] = cx[1];
    } else {
		rgb[0] = cx[0];
		rgb[2] = cx[1];
    }
	rgb[0] += v-cx[1];
	rgb[1] += v-cx[1];
	rgb[2] += v-cx[1];
    return rgb;
}

double getColorForValue(double value) {
	int diff = value - min;
	int range =  max - min;
	value = (1./range)*diff;
	return value;
}


double* getVChannel(double value) {
	double hsv[3] = { 0.5, 0.5, getColorForValue(value)};
	return hsv2rgb(hsv);
}

double* getSChannel(double value) {
	double hsv[3] = { 0.5,getColorForValue (value), 0.5};
	return hsv2rgb(hsv);
}