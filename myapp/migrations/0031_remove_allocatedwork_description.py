# Generated by Django 4.0.1 on 2024-03-20 17:07

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0030_allocatedwork_description'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='allocatedwork',
            name='description',
        ),
    ]
