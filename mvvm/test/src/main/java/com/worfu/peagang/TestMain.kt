package com.worfu.peagang

/**
 * @ClassName TestMain
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/11 10:26
 * @Version 1.0
 */



class User<T>(tClass:Class<T>){

    private var clazz:Class<T>?=null

    init {
       clazz =tClass
    }




    override fun toString(): String {

//        clazz?.kotlin?.memberProperties?.onEach {

//            System.out.println("get prop name:${it}")
//            val a = it.parameters[0]

//            System.out.println("get first prop:${a}}")
//        }



        val filed =   clazz?.getDeclaredField("sex")

        filed?.isAccessible =true
        val a = clazz?.newInstance()
        filed?.set(a,"ceshi")
        System.out.println("get first prop:${filed?.get(a)}")

        return super.toString()
    }
}


open class Student{

     var name:String?="hfcai"
     var sex:String?="sex"
}

open class B: Student(){

}


class C: B(){}
fun main(args: Array<String>) {
     val t = User(Student().javaClass)

    val s = Student()

    val b = B()
    val c = C()



    System.out.println("b is Student:${b is Student},and c is student:${c is Student}")


}


