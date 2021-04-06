#include "utilityclass.h"
#include <math.h>
#include <cmath>

UtilityClass::UtilityClass()
{

}



/**
 * @brief UtilityClass::BL2xy
 * @param L0 中央子午线经度  单位度
 * @param B
 * @param L
 * @param x
 * @param y
 */
void UtilityClass::BL2xy(TRANSLATE_TYPE type, double L0, double B, double L, double& x, double& y)
{
    double XX, N, t, t2, m, m2, ng2;
    double sinB, cosB;

    double a,f,e2,e12;
    double A1,A2,A3,A4;

    L0 = L0*PI/180.0;

    switch (type) {
    case TRANSLATE_TYPE::KRASOVSKY:
        a = 6378245;
        f = 298.3;
        e2 = 1 - ((f - 1) / f) * ((f - 1) / f);
        e12 = (f / (f - 1)) * (f / (f - 1)) - 1;
        A1 = 111134.8611;
        A2 = -16036.4803;
        A3 = 16.8281;
        A4 = -0.0220;
        break;

    case TRANSLATE_TYPE::IUGG1975:

        a = 6378140;
        f = 298.257;
        e2 = 1 - ((f - 1) / f) * ((f - 1) / f);
        e12 = (f / (f - 1)) * (f / (f - 1)) - 1;
        A1 = 111133.0047;  //
        A2 = -16038.5282;
        A3 = 16.8326;
        A4 = -0.0220;
        break;
    }


    B = B * PI/180.0;
    L = L * PI/180.0;

    XX = A1 * B * 180.0 / PI + A2 * sin(2 * B) + A3 * sin(4 * B) + A4 * sin(6 * B);
    sinB = sin(B);
    cosB = cos(B);
    t = tan(B);
    t2 = t * t;
    N = a / sqrt(1 - e2 * sinB * sinB);
    m = cosB * (L - L0);
    m2 = m * m;
    ng2 = cosB * cosB * e2 / (1 - e2);
    // x,y的计算公式见孔祥元等主编武汉大学出版社2002年出版的《控制测量学》
    x = XX + N * t * ((0.5 + ((5 - t2 + 9 * ng2 + 4 * ng2 * ng2) / 24.0 + (61 - 58 * t2 + t2 * t2) * m2 / 720.0) * m2) * m2);
    y = N * m * ( 1 + m2 * ( (1 - t2 + ng2) / 6.0 + m2 * ( 5 - 18 * t2 + t2 * t2 + 14 * ng2 - 58 * ng2 * t2 ) / 120.0));
    y = y + 500000;
}

/**
 * @brief UtilityClass::xy2BL
 * @param L0
 * @param x
 * @param y
 * @param B
 * @param L
 */
void UtilityClass::xy2BL(TRANSLATE_TYPE type, double L0, double x, double y, double& B, double& L)
{
    double sinB, cosB, t, t2, N ,ng2, V, yN;
    double preB0, B0;
    double eta;

    double a,f,e2,e12;
    double A1,A2,A3,A4;

    L0 = L0*PI/180.0;

    switch (type) {
    case TRANSLATE_TYPE.KRASOVSKY:
        a = 6378245;
        f = 298.3;
        e2 = 1 - ((f - 1) / f) * ((f - 1) / f);
        e12 = (f / (f - 1)) * (f / (f - 1)) - 1;
        A1 = 111134.8611;
        A2 = -16036.4803;
        A3 = 16.8281;
        A4 = -0.0220;
        break;

    case TRANSLATE_TYPE::IUGG1975:

        a = 6378140;
        f = 298.257;
        e2 = 1 - ((f - 1) / f) * ((f - 1) / f);
        e12 = (f / (f - 1)) * (f / (f - 1)) - 1;
        A1 = 111133.0047;  //
        A2 = -16038.5282;
        A3 = 16.8326;
        A4 = -0.0220;
        break;
    }

    y = y - 500000;
    B0 = x / A1;
    do{
        preB0 = B0;
        B0 = B0 * PI / 180.0;
        B0 = (x - (A2 * sin(2 * B0) + A3 * sin(4 * B0) + A4 * sin(6 * B0))) / A1;
        eta = abs(B0 - preB0);
    }
    while(eta > 0.000000001);

    B0 = B0 * PI / 180.0;
    B = Rad2Dms(B0);
    sinB = sin(B0);
    cosB = cos(B0);
    t = tan(B0);
    t2 = t * t;
    N = a / sqrt(1 - e2 * sinB * sinB);
    ng2 = cosB * cosB * e2 / (1 - e2);
    V = sqrt(1 + ng2);
    yN = y / N;
    B = B0 - (yN * yN - (5 + 3 * t2 + ng2 - 9 * ng2 * t2) * yN * yN * yN * yN / 12.0 + (61 + 90 * t2 + 45 * t2 * t2) * yN * yN * yN * yN * yN * yN / 360.0) * V * V * t / 2;
    L = L0 + (yN - (1 + 2 * t2 + ng2) * yN * yN * yN / 6.0 + (5 + 28 * t2 + 24 * t2 * t2 + 6 * ng2 + 8 * ng2 * t2) * yN * yN * yN * yN * yN / 120.0) / cosB;

    //B:=Rad2Dms(B);
    //L:=Rad2Dms(L);
    B =B*180.0/PI;
    L =L*180.0/PI;
}
