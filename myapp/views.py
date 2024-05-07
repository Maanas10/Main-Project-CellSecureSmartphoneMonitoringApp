import datetime

from django.conf import settings
from django.core.files.storage import FileSystemStorage
from django.core.mail import send_mail
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render, redirect

# Create your views here.
from myapp.models import *

def index(request):
    return render(request,"index.html")

def dashnew(request):
    return render(request,"admin/dashnew12.html")


def empdash(request):
    return render(request,"employee/empdash.html")

def empdash12(request):
    return render(request,"employee/empdash12.html")

def logout(request):
    request.session['lid']=''
    return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')


def login(request):
    return render(request,"Loginform.html")
def login_post(request):
    usrname=request.POST['usrname']
    password=request.POST['pwd']
    cls=Login.objects.filter(username=usrname,password=password)
    if cls.exists():
        cls = Login.objects.get(username=usrname, password=password)
        if cls.username != usrname or cls.password != password:
            return HttpResponse('''<Script>alert("Invalid user and password!");window.location="/myapp/login/"</Script>''')
        request.session['lid']=cls.id
        if cls.type =='admin':
            return HttpResponse('''<script>alert("Login Successfully");window.location="/myapp/Admindashboard/"</script>''')
        elif cls.type=='employee':
            return HttpResponse('''<script>alert("Login Successfully");window.location="/myapp/empdash/"</script>''')

        else :
            return HttpResponse('''<script>alert("User not found");window.location="/myapp/login/"</script>''')
    else :
        return HttpResponse('''<script>alert("Invalid Username/Password..Try again..........");window.location="/myapp/login/"</script>''')


def respassword(request):
    return render(request,"password.html")

def respassword_post(request):
    em = request.POST['usrname']
    id=Login.objects.get(username=em).id
    name=Employee.objects.get(LOGIN_id=id).f_name

    import random
    password = random.randint(00000000, 99999999)
    log = Login.objects.filter(username=em)
    print(em)
    if log.exists():
        logg = Login.objects.get(username=em)
        message = 'Dear '+str(name)+',\n\n' 'We have received a request to reset your password for CellSecure.Your new password is:'  + str(password)+ '\n\n To ensure the security of your account, we recommend changing this password immediately after logging in. \n\n  To proceed with the password reset, please follow the instructions below:\n\n  1.Click on the following link to access the login page: http://127.0.0.1:8000/myapp/login/ \n 2.Enter your email address and the new password provided in this email.\n\n If you did not initiate this password reset request, please contact our support team immediately at cellsecure.org@gmail.com\n Thank you for using Cellsecure\n\n\n Best regards\n Admin\n CellSecure'


        send_mail(
            'temp password',
            message,
            settings.EMAIL_HOST_USER,
            [em, ],
            fail_silently=False
        )
        logg.password = password
        logg.save()
        return HttpResponse('<script>alert("success");window.location="/myapp/login/"</script>')
    else:
        return HttpResponse('<script>alert("invalid email");window.location="/myapp/login/"</script>')

def add_employee(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')

    return render(request,"admin/addemployee.html")
def addemployee_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    fname=request.POST['fname']
    lname=request.POST['lname']
    email=request.POST['email']
    place=request.POST['place']
    post=request.POST['post']
    pin=request.POST['pin']
    gender=request.POST['gender']
    photo=request.FILES['photo']
    dob=request.POST['date']
    phone=request.POST['phone']
    desigination=request.POST['desigination']
    Imei=request.POST['Imei']
    modelname=request.POST['modelname']
    password=request.POST['password']

    from datetime import datetime
    date=datetime.now().strftime('%Y%m%d-%H%M%S')+'.jpg'
    fs=FileSystemStorage()
    fs.save(date,photo)
    path=fs.url(date)
    res=Employee.objects.filter(email=email)
    if res.exists():
        return HttpResponse('''<script>alert("Employee already Exists");window.location="/myapp/addemployee/"</script>''')
    else:
        b=Login()
        b.username=email
        b.password=password
        b.type='employee'
        b.save()
        a=Employee()
        a.f_name=fname
        a.l_name=lname
        a.email=email
        a.place=place
        a.post=post
        a.pin=pin
        a.gender=gender
        a.photo=path
        a.dob=dob
        a.phonenumber=phone
        a.desigination=desigination
        a.phone_imei=Imei
        a.phone_modelname=modelname
        # a.password=password
        a.LOGIN_id=b.id
        a.save()



        return HttpResponse('''<script>alert(" Employee Added Successfully");window.location="/myapp/addemployee/#12"</script>''')





def admin_dashboard(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    return render(request,"admin/dashnew12.html")


def employee_view(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    emp=Employee.objects.all()
    return render(request,"admin/Employee View.html",{'data':emp})

def admin_employee_search(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    search=request.POST['search']

    emp = Employee.objects.filter(f_name__icontains=search)
    return render(request, "admin/Employee View.html",{'data':emp})


# def employeeview_post(request):
#     search=request.POST['search']
#     emp = Employee.objects.filter(f_name__icontains=search)
#     return render(request,"admin/Employee View.html",{'data':emp})

# def searchemp_post(request):

def employee_delete(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    s=Employee.objects.get(LOGIN_id=id).delete()
    empdel=Login.objects.get(id=id).delete()
    return HttpResponse('''<script>alert("Deleted Successfully");window.location="/myapp/viewemployee/"</script>''')

def edit_employee(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    d=Employee.objects.get(LOGIN__id=id)
    return render(request,"admin/editemployee.html",{'data':d})


# add employee email validation
def addemp(request):
    email  = request.POST['email']
    print(email)
    data = {
        'is_taken': str(Login.objects.filter(username__iexact=email).exists())
    }
    # if data['is_taken']:
    #     data['is_taken']="True"
    print(data['is_taken'], 'dataaaa')
    return JsonResponse({"status":str(data['is_taken']),"data":data})

def checkimei(request):
    Imei = request.POST['Imei']
    print(Imei)
    data = {
        'is_taken': str(Employee.objects.filter(phone_imei__iexact=Imei).exists())
    }
    # if data['is_taken']:
    #     data['is_taken']="True"
    print(data['is_taken'], 'dataaaa')
    return JsonResponse({"status": str(data['is_taken']), "data": data})



def editemployee_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    fname = request.POST['fname']
    lname = request.POST['lname']
    email = request.POST['email']
    place = request.POST['place']
    post = request.POST['post']
    pin = request.POST['pin']
    gender = request.POST['gender']
    dob = request.POST['date']
    phone = request.POST['phone']
    desigination = request.POST['desigination']
    Imei = request.POST['Imei']
    modelname = request.POST['modelname']
    id = request.POST['id']

    lobj=Login.objects.get(id=id)
    lobj.username=email
    lobj.save()

    if 'photo' in request.FILES:
        photo = request.FILES['photo']

        from datetime import datetime
        date = datetime.now().strftime('%Y%m%d-%H%M%S') + '.jpg'
        fs = FileSystemStorage()
        fs.save(date, photo)
        path = fs.url(date)


        d=Employee.objects.get(LOGIN__id=id)
        d.f_name = fname
        d.l_name = lname
        d.email = email
        d.place = place
        d.post = post
        d.pin = pin
        d.gender = gender
        d.photo = path
        d.dob = dob
        d.phone = phone
        d.desigination = desigination
        d.phone_imei = Imei
        d.phone_modelname = modelname
        # a.password=password
        d.save()
        return HttpResponse('''<script>alert("Updated Successfully");window.location="/myapp/viewemployee/#12"</script>''')

    else:
        d=Employee.objects.get(LOGIN__id=id)
        d.f_name = fname
        d.l_name = lname
        d.email = email
        d.place = place
        d.post = post
        d.pin = pin
        d.gender = gender
        d.dob = dob
        d.phone = phone
        d.desigination = desigination
        d.phone_imei = Imei
        d.phone_modelname = modelname

        d.save()

    return HttpResponse('''<script>alert("Updated Successfully");window.location="/myapp/viewemployee/#12"</script>''')


def addwork(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    return render(request,"admin/add work.html")
def addwork_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    from datetime import datetime
    date=datetime.now().date().today()
    title=request.POST['title']
    desc=request.POST['desc']
    addwork=Work()
    addwork.date= date
    addwork.work_description= desc
    addwork.work_tittle=title
    addwork.save()
    return HttpResponse('''<script>alert("work added");window.location="/myapp/addwork/#12"</script>''')
def viewwork(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    wrk=Work.objects.all()
    return render(request,"admin/viewwork.html", {'data':wrk})
def edit_work(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    e=Work.objects.get(id=id)
    return render(request,"admin/edit work.html",{'data':e})
def editwork_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    id=request.POST['id']
    from datetime import datetime
    date=datetime.now().date().today()
    title=request.POST['title']
    desc=request.POST['desc']
    addwork=Work.objects.get(id=id)
    addwork.date= date
    addwork.work_description= desc
    addwork.work_tittle=title
    addwork.save()
    return HttpResponse('''<script>alert("Edited Successfully");window.location="/myapp/viewwork/#12"</script>''')

def work_delete(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    s=Work.objects.get(id=id).delete()
    # workdel=Work.objects.get(id=id).delete()
    return HttpResponse('''<script>alert("Deleted Successfully");window.location="/myapp/viewwork/#12"</script>''')

def allocate_work(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    c=Employee.objects.all()
    d=Work.objects.all()
    return render(request,"admin/Allocate work.html",{'data':c,'data1':d})
def allocatework_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    emp=request.POST['employee']
    work=request.POST['work']
    if AllocatedWork.objects.filter(WORK_id=work, EMPLOYEE_id=emp).exists():
        return HttpResponse('''<script>alert("already allocated");window.location="/myapp/allocatework/#12"</script>''')

    alldate=request.POST['alldate']
    dddate=request.POST['dddate']
    obj=AllocatedWork()
    obj.allocated_date=alldate
    obj.deadline=dddate
    obj.EMPLOYEE_id=emp
    obj.WORK_id=work
    obj.work_status="Open"
    obj.description="Pending"

    obj.save()
    return HttpResponse('''<script>alert("Allocated work to Employee Successfully");window.location="/myapp/viewallocatedwork/#12"</script>''')

def viewallocatedwork(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    wrks=AllocatedWork.objects.all()
    return render(request,"admin/viewallocatedwork.html",{'data':wrks})
def viewallocattedwork_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    fdate=request.POST['fdate']
    tdate=request.POST['tdate']
    wrks=AllocatedWork.objects.filter(deadline__range=[fdate,tdate])
    return render(request,"admin/viewallocatedwork.html",{'data':wrks})





def editallocatedwork(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    b=AllocatedWork.objects.get(id=id)
    w=Work.objects.all()
    e=Employee.objects.all()
    return render(request,"admin/editallocatedwork.html",{'data':b,'wdata':w,'edata':e})
def editallocatedwork_post(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    id=request.POST['id']
    emp=request.POST['employee']
    work = request.POST['work']
    alldate = request.POST['alldate']
    dddate = request.POST['dddate']
    obj = AllocatedWork.objects.get(id=id)
    obj.allocated_date = alldate
    obj.deadline = dddate
    obj.EMPLOYEE_id = emp
    obj.WORK_id = work
    obj.save()
    return HttpResponse('''<script>alert("Edited Successfully");window.location="/myapp/viewallocatedwork/#12"</script>''')
def allocatedwork_delete(request,id):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    s=AllocatedWork.objects.get(id=id).delete()
    return HttpResponse('''<script>alert("Deleted Successfully");window.location="/myapp/viewallocatedwork/#12"</script>''')

def managephones(request):
    mng = Employee.objects.all()
    return render(request,"admin/ManagePhones.html",{'data': mng})

def managephone_search(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    search=request.POST['search12']
    mng = Employee.objects.filter(f_name__icontains=search)
    return render(request, "admin/ManagePhones.html",{'data':mng})

def calllogs(request,id):
    cl=CallLogs.objects.filter(EMPLOYEE_id=id).order_by('-date')
    return render(request,"admin/calllogs.html",{'data' : cl})



def insertcalllogs_post(request):
    imei=request.POST['imei']
    phonenumber=request.POST['phonenumber']
    type = request.POST['type']


    if CallLogs.objects.filter(phone_number='null').exists():
        return JsonResponse({'status':'no'})
    else:

        if phonenumber != "null":
            inc=CallLogs()
            inc.phone_number=phonenumber
            inc.type=type
            inc.EMPLOYEE=Employee.objects.get(phone_imei=imei)
            from datetime import datetime
            inc.date=datetime.now().strftime('%Y%m%d-%H%M%S')
            inc.save()
        return JsonResponse({'status':'ok'})

def calllog_search(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    # search=request.POST['call_type']

    fromdate = request.POST['fromdate']
    todate = request.POST['todate']

    # call = CallLogs.objects.filter(type__icontains=search)
    cll = CallLogs.objects.filter(date__range=[fromdate, todate])

    if cll !="":
        cll = CallLogs.objects.filter(date__range=[fromdate, todate])

        return render(request, "admin/calllogs.html",{'data':cll})
    # else:
        # return render(request, "admin/calllogs.html",{'data':call})

def calllog_search1(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    search=request.POST['call_type']
    call = CallLogs.objects.filter(type__icontains=search)
    return render(request, "admin/calllogs.html", {'data': call})










def location(request,id):
    lc = Location.objects.filter(EMPLOYEE_id=id).order_by('-date')
    return render(request,"admin/location.html" , {'data': lc})

def location_post(request):
    imei=request.POST['imei']
    place=request.POST['place']
    longitude=request.POST['Longitude']
    lattitude=request.POST['Latitude']

    plc=Zone.objects.filter(EMPLOYEE__phone_imei=imei)
    if plc.exists():
        if plc[0].place != place:
            lc=Zonechange()
            lc.lattitude=lattitude
            lc.longitude=longitude
            lc.place=place
            lc.notification='Zone changed'
            lc.EMPLOYEE = Employee.objects.get(phone_imei=imei)
            from datetime import datetime
            lc.date = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            lc.save()
    print(place)
    loc=Location()
    loc.lattitude=lattitude
    from datetime import datetime
    loc.date=datetime.now().strftime('%Y%m%d-%H%M%S')
    loc.longitude=longitude
    loc.place=place
    loc.EMPLOYEE=Employee.objects.get(phone_imei=imei)
    loc.save()
    return JsonResponse({'status':'ok'})

def message(request,id):
    ms = MessageLogs.objects.filter(EMPLOYEE_id=id).order_by('-date')
    return render(request,"admin/messagelogs.html",  {'data': ms})


def message_post(request):
    imei=request.POST['imei']
    phonenumber=request.POST['phonenumber']
    type=request.POST['type']
    sms=request.POST['sms']
    mes=MessageLogs()
    mes.phonenumber=phonenumber
    mes.sms_info=sms
    mes.type=type
    mes.EMPLOYEE=Employee.objects.get(phone_imei=imei)
    from datetime import datetime
    mes.date=datetime.now().strftime('%Y%m%d-%H%M%S')
    mes.save()
    return JsonResponse({'status':'ok'})

def messagelog_search(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')

    fromdate = request.POST['fromdate']
    todate = request.POST['todate']

    cll = MessageLogs.objects.filter(date__range=[fromdate, todate])

    if cll !="":
        cll = MessageLogs.objects.filter(date__range=[fromdate, todate])

        return render(request, "admin/messagelogs.html",{'data':cll})

def messagelog_search1(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    search=request.POST['ms_type']
    call = MessageLogs.objects.filter(type__icontains=search)
    return render(request, "admin/messagelogs.html", {'data': call})




def viewprofile(request,id):
    vw=Employee.objects.get(id=id)
    return render(request,"admin/profile.html",{'data':vw})

def simchange(request,id):

    ss = Simchange.objects.filter(EMPLOYEE_id=id).order_by('-sim_date')

    return render(request,"admin/simchange.html",{'data': ss})
def insertsimchange_post(request):
    imei=request.POST['imei']
    import  datetime
    sim_date=datetime.datetime.now()
    notification='New Sim has been detected'
    sim=Simchange()
    sim.sim_date=sim_date
    sim.notification=notification
    sim.EMPLOYEE=Employee.objects.get(phone_imei=imei)
    from datetime import datetime
    sim.date = datetime.now().strftime('%Y%m%d-%H%M%S')
    sim.save()
    return JsonResponse({'status': 'ok'})

def zoneadd(request):

    return render(request,"admin/zoneadd.html")

def zoneadd_post(request):

    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')

    place=request.POST['place']
    imei = request.session['imei']
    import datetime
    date = datetime.datetime.now()
    addzone=Zone()
    if Zone.objects.filter(EMPLOYEE=Employee.objects.get(phone_imei=imei)).exists():
        addzone = Zone.objects.filter(EMPLOYEE=Employee.objects.get(phone_imei=imei))[0]
    addzone.place= place
    addzone.date = date
    addzone.EMPLOYEE=Employee.objects.get(phone_imei=imei)

    addzone.save()
    return HttpResponse('''<script>alert("Zone Set Successfully");window.location="/myapp/managephones/#12"</script>''')

def zonechange(request,id):
    request.session['imei'] = Employee.objects.get(id=id).phone_imei
    zn = Zonechange.objects.filter(EMPLOYEE_id=id).order_by('-date')
    try:
        vv = Zone.objects.get(EMPLOYEE_id=id).place
    except:
        vv = 'no zone Set'

    print(vv)
    return render(request,"admin/zonechange.html",{'data':zn,'z':vv})





def profilesettings(request,id):
    request.session['imei'] = Employee.objects.get(id=id).phone_imei
    p = {}
    if Profilesettings.objects.filter(EMPLOYEE_id=id).exists():
        p = Profilesettings.objects.filter(EMPLOYEE_id=id)[0]


    return render(request,"admin/profilesettings.html",{"imei":Employee.objects.get(id=id).phone_imei, 'data':p})


def profilesettings_post(request):
    sdcard=request.POST['sdcard']
    touch=request.POST['touch']
    bright=request.POST['bright']
    ring=request.POST['ring']
    wallpaper=request.POST['wallpaper']
    imei=request.POST['imei']
    p=Profilesettings()
    if Profilesettings.objects.filter(EMPLOYEE = Employee.objects.get(phone_imei=imei)).exists():
        p = Profilesettings.objects.filter(EMPLOYEE=Employee.objects.get(phone_imei=imei))[0]
    p.sdcard=sdcard
    p.ringmode=ring
    p.touchmode=touch
    p.brightness=bright
    p.wallpaper=wallpaper
    p.EMPLOYEE = Employee.objects.get(phone_imei=imei)
    p.save()
    return HttpResponse('''<script>alert("Profile Setings Updated Successfully");window.location="/myapp/managephones/#12"</script>''')


def admcomplaint(request):
    cmp=Complaint.objects.all

    return render(request,"admin/complaint.html",{'data':cmp})

def admreply(request,id):

    return render(request,"admin/replycomplaint.html" ,{'id':id})

def admreply_post(request):

    complaint=request.POST['complaint']
    id=request.POST['id']
    print(id)
    gg=Complaint.objects.get(id=id)
    gg.reply=complaint
    gg.save()
    return HttpResponse('''<script>alert("Replied to Complaint successfull");window.location="/myapp/admcomplaint/#12"</script>''')







def profileset(request):
    imei=request.POST['imei']
    l = []
    ph = Profilesettings.objects.filter(EMPLOYEE__phone_imei=imei)
    print(ph)
    for i in ph:
        l.append({
            'sdcard':i.sdcard,'ringmode':i.ringmode,'brightness':i.brightness,'touchmode':i.touchmode,'wallpaper':i.wallpaper,
        })
    return JsonResponse({'status':'ok','data':l[0]})


def employeeviewprofile(request):
    em = Employee.objects.get(LOGIN_id=request.session['lid'])

    return render(request, "employee/employeeviewprofile.html", {'data':em})

def password(request):


    return render(request, "employee/password.html")

def password_post(request):
    password=request.POST['password']
    confirmpassword=request.POST['password1']
    cpassword=request.POST['cpassword']
    clsa=Login.objects.filter(id=request.session['lid'],password=password)
    if clsa.exists():
        cls = Login.objects.get(id=request.session['lid'], password=password)
        if cls.password != password:
            return HttpResponse('''<Script>alert("Invalid current password");window.location="/myapp/password/"</Script>''')
        elif confirmpassword != cpassword:
            return HttpResponse('''<Script>alert("Password Missmatch");window.location="/myapp/password/"</Script>''')

        else :
            clsa.update(password=confirmpassword)
            return HttpResponse('''<script>alert("Password Changed");window.location="/myapp/employeeviewprofile/"</script>''')
    else :
        return HttpResponse('''<script>alert("Invalid password");window.location="/myapp/password/"</script>''')

def passwordvali(request):
    password = request.POST['password']
    print(password)
    data = {
        'is_taken': str(Login.objects.filter(password__iexact=password).exists())
    }
    # if data['is_taken']:
    #     data['is_taken']="True"
    print(data['is_taken'], 'dataaaa')
    return JsonResponse({"status": str(data['is_taken']), "data": data})


def employeework(request):
    em = AllocatedWork.objects.filter(EMPLOYEE__LOGIN_id=request.session['lid'])

    return render(request,"employee/employeework.html", {'data':em})

def employeework_post(request):

    fdate=request.POST['frdate']
    tdate=request.POST['todate']
    wrks=AllocatedWork.objects.filter(EMPLOYEE__LOGIN_id=request.session['lid'],deadline__range=[fdate,tdate])
    return render(request,"employee/employeework.html",{'data':wrks})

def workstatus(request,id):
    return render(request,"employee/workstatus.html",{'id':id})

def workstatus_post(request):
    work=request.POST['work']
    desc=request.POST['desc']
    id=request.POST['id']
    cls = Employee.objects.get(LOGIN_id=request.session['lid'])
    gg=AllocatedWork.objects.get(id=id)
    gg.work_status=work
    gg.description=desc
    gg.EMPLOYEE=cls
    gg.save()
    return HttpResponse('''<script>alert("Work status updated");window.location="/myapp/employeework/#12"</script>''')

def complaint(request):
    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    return render(request,"employee/complaint.html")
def complaint_post(request):
    complaint=request.POST['complaint']
    incident=request.POST['incident']
    cls = Employee.objects.get(LOGIN_id=request.session['lid'])
    gg=Complaint()
    gg.date=datetime.date.today()
    gg.c_description=complaint
    gg.c_incident=incident
    gg.EMPLOYEE=cls
    gg.save()
    return HttpResponse('''<script>alert("Complaint Submited");window.location="/myapp/viewreply/#12"</script>''')

def viewreply(request):

    if request.session['lid']=='':
        return HttpResponse('''<Script>alert("Logout!");window.location="/myapp/login/"</Script>''')
    em = Complaint.objects.filter(EMPLOYEE__LOGIN_id=request.session['lid'])

    return render(request,"employee/viewreply.html",{'data':em})





































