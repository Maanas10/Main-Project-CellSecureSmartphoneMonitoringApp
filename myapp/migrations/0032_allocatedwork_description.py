# Generated by Django 4.0.1 on 2024-03-20 17:08

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0031_remove_allocatedwork_description'),
    ]

    operations = [
        migrations.AddField(
            model_name='allocatedwork',
            name='description',
            field=models.CharField(default=1, max_length=30),
            preserve_default=False,
        ),
    ]
