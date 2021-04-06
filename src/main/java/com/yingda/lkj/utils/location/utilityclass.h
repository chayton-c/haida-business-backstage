#ifndef UTILITYCLASS_H
#define UTILITYCLASS_H


#define PI                      3.1415926535897932384626433832795
#define EARTH_RADIUS            6378.137        //地球近似半径
const double pi = 3.14159265358979324;
const double a = 6378245.0;
const double ee = 0.00669342162296594323;
const double x_pi = 3.1415926535897932384626433832795 * 3000.0 / 180.0;

// 转换类型
enum TRANSLATE_TYPE{
    KRASOVSKY,
    IUGG1975
};

class UtilityClass
{
public:
    UtilityClass();

public:
    double static getDistance(double lat1, double lng1, double lat2, double lng2);
    double static getAngle(double lat1, double lng1, double lat2, double lng2);
    void static transform2Mars(double wgLat, double wgLon, double &mgLat, double &mgLon);
    void static bd_encrypt(double gg_lat, double gg_lon, double &bd_lat, double &bd_lon);
    void static bd_decrypt(double bd_lat, double bd_lon, double &gg_lat, double &gg_lon);
    bool static outOfChina(double lat, double lon);
    // 经纬度转高斯坐标
    void static BL2xy(TRANSLATE_TYPE type, double L0, double B, double L, double& x, double& y);
    void static xy2BL(TRANSLATE_TYPE type, double L0, double x, double y, double& B, double& L);

private:
    double static transformLat(double x, double y);
    double static transformLon(double x, double y);
    double static Dms2Rad(double Dms);  // 度分秒转弧度
    double static Rad2Dms(double Rad);  // 弧度转度分秒
};

#endif // UTILITYCLASS_H
