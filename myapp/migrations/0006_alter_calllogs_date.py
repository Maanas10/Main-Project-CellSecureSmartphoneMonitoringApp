# Generated by Django 4.0.1 on 2024-02-19 10:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0005_messagelogs_phonenumber'),
    ]

    operations = [
        migrations.AlterField(
            model_name='calllogs',
            name='date',
            field=models.CharField(max_length=30),
        ),
    ]
