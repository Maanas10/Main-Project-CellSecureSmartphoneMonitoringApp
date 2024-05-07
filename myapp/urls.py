
from django.contrib import admin
from django.urls import path

from myapp import views

urlpatterns = [
    path('home/',views.index,name='index'),
    path('login/',views.login),
    path('respassword/',views.respassword),
    path('respassword_post/',views.respassword_post),

    path('logout/',views.logout),
    path('login_post/',views.login_post),
    path('Admindashboard/',views.admin_dashboard),
    path('empdash12/',views.empdash12),
    path('addemployee/', views.add_employee),
    path('addemployee_post/', views.addemployee_post),
    path('viewemployee/', views.employee_view),
    path('admin_employee_search/', views.admin_employee_search),

    path('deleteemployee/<id>', views.employee_delete),
    path('editemployee/<id>',views.edit_employee),
    path('editemployee_post/',views.editemployee_post),
    # path('employeeview_post/',views.employeeview_post),

    path('dashnew/',views.dashnew),
    path('empdash/',views.empdash),
    path('addwork/',views.addwork),
    path('addwork_post/',views.addwork_post),
    path('viewwork/', views.viewwork),
    path('editwork/<id>',views.edit_work),
    path('editwork_post/',views.editwork_post),
    path('work_delete/<id>',views.work_delete),
    path('allocatework/',views.allocate_work),
    path('allocatework_post/',views.allocatework_post),
    path('viewallocatedwork/',views.viewallocatedwork),
    path('viewallocatedwork_post/',views.viewallocattedwork_post),
    path('editallocatedwork/<id>',views.editallocatedwork),
    path('editallocatedwork_post/',views.editallocatedwork_post),
    path('allocatedwork_delete/<id>',views.allocatedwork_delete),
    path('managephones/',views.managephones),
    path('managephone_search/', views.managephone_search),

    path('calllogs/<id>',views.calllogs),
    path('calllog_search/',views.calllog_search),
    path('calllog_search1/',views.calllog_search1),
    path('insertcalllogs_post/',views.insertcalllogs_post),
    path('location/',views.location_post),
    path('messagelogs/',views.message_post),
    path('message/<id>',views.message),
    path('messagelog_search/',views.messagelog_search),
    path('messagelog_search1/',views.messagelog_search1),

    path('locationview/<id>',views.location),
    path('addemp/',views.addemp),
    path('viewprofile/<id>',views.viewprofile),
    path('checkimei/',views.checkimei),
    path('simchange/<id>',views.simchange),
    path('simchange_post/',views.insertsimchange_post),
    path('zoneadd/', views.zoneadd),
    path('zoneadd_post/',views.zoneadd_post),
    path('zonechange/<id>', views.zonechange),
    path('admcomplaint/', views.admcomplaint),
    path('admreply/<id>', views.admreply),
    path('admreply_post/', views.admreply_post),


    path('profilesettings/<id>',views.profilesettings),
    path('profilesettings_post/',views.profilesettings_post),
    path('profileset/', views.profileset),
    path('employeeviewprofile/', views.employeeviewprofile),
    path('password/', views.password),
    path('password_post/', views.password_post),
    path('passwordvali/', views.passwordvali),


    path('employeework/',views.employeework),
    path('employeework_post/',views.employeework_post),
    path('workstatus/<id>',views.workstatus),
    path('workstatus_post/', views.workstatus_post),
    path('complaint/', views.complaint),
    path('complaint_post/', views.complaint_post),
    path('viewreply/', views.viewreply),

]
