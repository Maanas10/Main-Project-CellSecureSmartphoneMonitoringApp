# Generated by Django 4.0.1 on 2024-03-20 17:08

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0032_allocatedwork_description'),
    ]

    operations = [
        migrations.AlterField(
            model_name='allocatedwork',
            name='description',
            field=models.CharField(max_length=3000),
        ),
    ]