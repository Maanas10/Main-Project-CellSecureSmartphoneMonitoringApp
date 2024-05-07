# Generated by Django 4.0.1 on 2024-03-20 16:14

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0023_delete_simchange'),
    ]

    operations = [
        migrations.CreateModel(
            name='Simchange',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('notification', models.CharField(max_length=500)),
                ('sim_date', models.DateTimeField()),
                ('EMPLOYEE', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='myapp.employee')),
            ],
        ),
    ]