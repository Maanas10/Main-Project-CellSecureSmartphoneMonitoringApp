# Generated by Django 4.0.1 on 2024-02-04 09:53

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='employee',
            name='password',
        ),
    ]
