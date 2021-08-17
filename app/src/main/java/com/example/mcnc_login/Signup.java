package com.example.mcnc_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText et_email, et_password, et_password_check;
    Button btn_signup;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //파이어베이스 접근 설정
        firebaseAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_password_check = findViewById(R.id.et_password_check);
        btn_signup = findViewById(R.id.btn_signup);

        //가입버튼 클릭리스너 -> firebase 데이터를 저장
        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //가입 정보 가져오기
                final String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password_check = et_password_check.getText().toString().trim();

                if (password.equals(password_check)) {
                    Log.d("Signup", "등록 버튼 " + email + " , " + password);
                    final ProgressDialog mDialog = new ProgressDialog(Signup.this);
                    mDialog.setMessage("가입중입니다.");
                    mDialog.show();

                    //firebase 신규계정 등록
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공시,
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();

                                //hash map table -> firebase 데이터베이스에 저장
                                HashMap<Object, String> hashMap = new HashMap<>();

                                hashMap.put("uid", uid);
                                hashMap.put("email", email);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);


                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(Signup.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Signup.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });

                    //비밀번호 오류시
                } else {

                    Toast.makeText(Signup.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        ; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}