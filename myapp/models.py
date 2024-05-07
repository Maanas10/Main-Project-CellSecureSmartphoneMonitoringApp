from django.db import models

# Create your models here.
class Login(models.Model):
    username=models.CharField(max_length=25)
    password=models.CharField(max_length=25)
    type=models.CharField(max_length=10)

class Employee(models.Model):
    f_name=models.CharField(max_length=30)
    l_name=models.CharField(max_length=30)
    email=models.CharField(max_length=30)
    place=models.CharField(max_length=30)
    post=models.CharField(max_length=30)
    pin=models.CharField(max_length=30)
    photo=models.CharField(max_length=500)
    phonenumber=models.CharField(max_length=30)
    dob=models.DateField()
    phone_imei=models.CharField(max_length=50)
    phone_modelname=models.CharField(max_length=80)
    gender=models.CharField(max_length=20)
    desigination=models.CharField(max_length=50)
    # password=models.CharField(max_length=30)
    LOGIN=models.ForeignKey(Login,on_delete=models.CASCADE)

class Work(models.Model):
    work_tittle=models.CharField(max_length=30)
    work_description=models.CharField(max_length=500)
    date=models.DateField()

class AllocatedWork(models.Model):
    work_status=models.CharField(max_length=30)
    description=models.CharField(max_length=3000)
    allocated_date=models.DateField()
    deadline=models.DateField()
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)
    WORK=models.ForeignKey(Work,on_delete=models.CASCADE)


class Report(models.Model):
    description=models.CharField(max_length=500)
    date=models.DateField()
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Complaint(models.Model):
    c_description=models.CharField(max_length=500)
    c_incident = models.CharField(max_length=500)

    reply=models.CharField(max_length=500)
    date=models.DateField()
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class CallLogs(models.Model):
    phone_number=models.CharField(max_length=30)
    type=models.CharField(max_length=30)
    date=models.DateTimeField()
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class MessageLogs(models.Model):
    sms_info=models.CharField(max_length=2000)
    type=models.CharField(max_length=30)
    date=models.DateTimeField()
    phonenumber=models.CharField(max_length=40)
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Location(models.Model):
    lattitude=models.CharField(max_length=300)
    longitude=models.CharField(max_length=300)
    date=models.DateTimeField()
    place=models.CharField(max_length=300)
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Zone(models.Model):
    lattitude=models.CharField(max_length=300)
    longitude=models.CharField(max_length=300)
    date=models.DateField()
    place=models.CharField(max_length=300)
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Profile(models.Model):
    wallpaper=models.CharField(max_length=300)
    touchmode=models.CharField(max_length=300)
    brightness=models.CharField(max_length=30)

class Simchange(models.Model):
    notification=models.CharField(max_length=500)
    sim_date=models.DateTimeField()
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Zonechange(models.Model):
    lattitude=models.CharField(max_length=300)
    longitude=models.CharField(max_length=300)
    date=models.DateTimeField()
    place=models.CharField(max_length=300)
    notification=models.CharField(max_length=300)
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)

class Profilesettings(models.Model):
    sdcard=models.CharField(max_length=300)
    ringmode=models.CharField(max_length=300)
    brightness=models.CharField(max_length=300)
    touchmode=models.CharField(max_length=300)
    wallpaper=models.CharField(max_length=300)
    EMPLOYEE=models.ForeignKey(Employee,on_delete=models.CASCADE)






































