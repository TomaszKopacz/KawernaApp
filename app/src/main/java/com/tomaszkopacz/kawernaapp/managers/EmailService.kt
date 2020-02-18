package com.tomaszkopacz.kawernaapp.managers

import co.nedim.maildroidx.MaildroidX
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailService @Inject constructor() {

    private val mailBuilder = MaildroidX.Builder()
        .smtp("smtp.poczta.onet.pl")
        .smtpUsername("kawerna.app@onet.pl")
        .smtpPassword("KawernaApp2020")
        .smtpAuthentication(true)
        .port("465")
        .from("kawerna.app@onet.pl")

    fun sendEmail(mail: String, subject: String, body: String) {
        mailBuilder
            .to(mail)
            .subject(subject)
            .body(body)
            .mail()
    }
}