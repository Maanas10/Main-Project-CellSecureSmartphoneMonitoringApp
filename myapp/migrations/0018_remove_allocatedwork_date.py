# Generated by Django 4.0.1 on 2024-03-20 14:45

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0017_rename_date_allocatedwork_date'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='allocatedwork',
            name='Date',
        ),
    ]
