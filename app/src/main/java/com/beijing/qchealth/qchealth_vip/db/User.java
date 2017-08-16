package com.beijing.qchealth.qchealth_vip.db;

/**
 * Created by lhy on 2017/7/4.
 */

public class User {
    /**
     * 
     * 
     * 	
     id	String	会员id	
     name	String	用户名	
     nickName	String	昵称	
     realName	String	真实姓名	
     password	String	密码	
     mobile	String	手机	
     gender	int	性别	1、男 2、女
     birthday	date	生日	
     nation	int	民族	
     state	int	激活状态	1、正常 0、禁用
     ringId	String	聊天ID	
     registrationId	String	极光ID	
     provinceCode	String	省份行政编码	
     provinceName	String	省份名称	
     cityCode	String	城市行政编码	
     cityName	String	城市名称	
     countyCode	String	县行政编码	
     countyName	String	县名称	
     address	String	详细地址	
     avatar	String	头像	
     stature	int	身高	
     weight	int	体重	
     education	int	学历	1、专科 2、本科 3、研究生及以上
     vocation	int	职业	1、办公室 2、教育、服务 3、户外
     createDate	date	注册时间	
     
     */

    private String id;
    private String name;
    private String nickName;
    private String realName;
    private String password;
    private String mobile;
    private int gender;
    private String birthday;
    private String nation;
    private int state;
    private String ringId;
    private String registrationId;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String countyCode;
    private String countyName;
    private String address;
    private String avatar;
    private String stature;
    private String weight;
    private int education;
    private int vocation;
    private long createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRingId() {
        return ringId;
    }

    public void setRingId(String ringId) {
        this.ringId = ringId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStature() {
        return stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getVocation() {
        return vocation;
    }

    public void setVocation(int vocation) {
        this.vocation = vocation;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", nation='").append(nation).append('\'');
        sb.append(", state=").append(state);
        sb.append(", ringId='").append(ringId).append('\'');
        sb.append(", registrationId='").append(registrationId).append('\'');
        sb.append(", provinceCode='").append(provinceCode).append('\'');
        sb.append(", provinceName='").append(provinceName).append('\'');
        sb.append(", cityCode='").append(cityCode).append('\'');
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", countyCode='").append(countyCode).append('\'');
        sb.append(", countyName='").append(countyName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", stature='").append(stature).append('\'');
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", education=").append(education);
        sb.append(", vocation=").append(vocation);
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }
}
