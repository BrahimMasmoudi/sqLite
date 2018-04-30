package com.example.brahimmasmoudi.sqllite

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.button_add
import kotlinx.android.synthetic.main.activity_main.button_delete
import kotlinx.android.synthetic.main.activity_main.button_update
import kotlinx.android.synthetic.main.activity_main.button_viewAll
import kotlinx.android.synthetic.main.activity_main.editText_Marks
import kotlinx.android.synthetic.main.activity_main.editText_id
import kotlinx.android.synthetic.main.activity_main.editText_name
import kotlinx.android.synthetic.main.activity_main.editText_surname


class MainActivity : AppCompatActivity() {
    lateinit var myDb: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDb = DatabaseHelper(this);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun DeleteData() {
        button_delete.setOnClickListener({
            fun onClick(v: View) {
                var deletedRows = myDb.deleteData(editText_id.text.toString())!!
                if (deletedRows > 0)
                    Toast.makeText(this@MainActivity, "Data Deleted", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this@MainActivity, "Data not Deleted", Toast.LENGTH_LONG).show()
            }
        }
        )
    }

    private fun UpdateData() {
        button_update.setOnClickListener(
                {
                    val isUpdate = myDb.updateData(editText_id.getText().toString(),
                            editText_name.getText().toString(),
                            editText_surname.getText().toString(), editText_Marks.getText().toString())
                    if (isUpdate)
                        Toast.makeText(this@MainActivity, "Data Update", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this@MainActivity, "Data not Updated", Toast.LENGTH_LONG).show()
                }
        )
    }

    private fun AddData() {
        button_add.setOnClickListener(
                {
                    val isInserted = myDb.insertData(editText_name.getText().toString(),
                            editText_surname.getText().toString(),
                            editText_Marks.getText().toString())
                    if (isInserted)
                    {
                        Toast.makeText(this@MainActivity, "Data Inserted", Toast.LENGTH_LONG).show()
                        editText_name.setText("")
                        editText_surname.setText("")
                        editText_Marks.setText("")
                        editText_id.setText("")
                    }

                    else
                        Toast.makeText(this@MainActivity, "Data not Inserted", Toast.LENGTH_LONG).show()
                }
        )


    }

    private fun viewAll() {
        button_viewAll.setOnClickListener(
                View.OnClickListener {
                    val res = myDb.getAllData()
                    if (res.count === 0) {
                        // show message
                        showMessage("Error", "Nothing found")
                        return@OnClickListener
                    }

                    val buffer = StringBuffer()
                    while (res.moveToNext()) {
                        buffer.append("Id :" + res.getString(0) + "\n")
                        buffer.append("Scenario :" + res.getString(1) + "\n")
                        buffer.append("DocumentType :" + res.getString(2) + "\n")
                        buffer.append("Connector :" + res.getString(3) + "\n\n")
                    }

                    // Show all data
                    showMessage("Data", buffer.toString())
                }
        )
    }

    private fun showMessage(title: String, Message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }


}
