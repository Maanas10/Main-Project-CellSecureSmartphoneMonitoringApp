# Generated by Django 4.0.1 on 2024-02-04 10:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0002_remove_employee_password'),
    ]

    operations = [
        migrations.AddField(
            model_name='employee',
            name='gender',
            field=models.CharField(default=1, max_length=20),
            preserve_default=False,
        ),
    ]
