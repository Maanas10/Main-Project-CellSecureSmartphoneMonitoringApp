# Generated by Django 4.0.1 on 2024-03-20 16:39

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0027_remove_allocatedwork_employee_and_more'),
    ]

    operations = [
        migrations.CreateModel(
            name='Employee',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('f_name', models.CharField(max_length=30)),
                ('l_name', models.CharField(max_length=30)),
                ('email', models.CharField(max_length=30)),
                ('place', models.CharField(max_length=30)),
                ('post', models.CharField(max_length=30)),
                ('pin', models.CharField(max_length=30)),
                ('photo', models.CharField(max_length=500)),
                ('phonenumber', models.CharField(max_length=30)),
                ('dob', models.DateField()),
                ('phone_imei', models.CharField(max_length=50)),
                ('phone_modelname', models.CharField(max_length=80)),
                ('gender', models.CharField(max_length=20)),
                ('desigination', models.CharField(max_length=50)),
                ('LOGIN', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.login')),
            ],
        ),
        migrations.CreateModel(
            name='Profile',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('wallpaper', models.CharField(max_length=300)),
                ('touchmode', models.CharField(max_length=300)),
                ('brightness', models.CharField(max_length=30)),
            ],
        ),
        migrations.CreateModel(
            name='Work',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('work_tittle', models.CharField(max_length=30)),
                ('work_description', models.CharField(max_length=500)),
                ('date', models.DateField()),
            ],
        ),
        migrations.CreateModel(
            name='Zonechange',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('lattitude', models.CharField(max_length=300)),
                ('longitude', models.CharField(max_length=300)),
                ('date', models.DateTimeField()),
                ('place', models.CharField(max_length=300)),
                ('notification', models.CharField(max_length=300)),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Zone',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('lattitude', models.CharField(max_length=300)),
                ('longitude', models.CharField(max_length=300)),
                ('date', models.DateField()),
                ('place', models.CharField(max_length=300)),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Simchange',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('notification', models.CharField(max_length=500)),
                ('sim_date', models.DateTimeField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Report',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('description', models.CharField(max_length=500)),
                ('date', models.DateField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Profilesettings',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('sdcard', models.CharField(max_length=300)),
                ('ringmode', models.CharField(max_length=300)),
                ('brightness', models.CharField(max_length=300)),
                ('touchmode', models.CharField(max_length=300)),
                ('wallpaper', models.CharField(max_length=300)),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='MessageLogs',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('sms_info', models.CharField(max_length=2000)),
                ('type', models.CharField(max_length=30)),
                ('date', models.DateTimeField()),
                ('phonenumber', models.CharField(max_length=40)),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Location',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('lattitude', models.CharField(max_length=300)),
                ('longitude', models.CharField(max_length=300)),
                ('date', models.DateTimeField()),
                ('place', models.CharField(max_length=300)),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='Complaint',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('c_description', models.CharField(max_length=500)),
                ('reply', models.CharField(max_length=500)),
                ('date', models.DateField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='CallLogs',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('phone_number', models.CharField(max_length=30)),
                ('type', models.CharField(max_length=30)),
                ('date', models.DateTimeField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
        migrations.CreateModel(
            name='AllocatedWork',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('work_status', models.CharField(max_length=30)),
                ('description', models.CharField(max_length=30)),
                ('allocated_date', models.DateField()),
                ('deadline', models.DateField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
                ('WORK', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.work')),
            ],
        ),
    ]
