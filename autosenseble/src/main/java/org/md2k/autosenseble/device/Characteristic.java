package org.md2k.autosenseble.device;
/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.polidea.rxandroidble.RxBleConnection;

import org.md2k.autosenseble.Data;
import org.md2k.datakitapi.time.DateTime;

import java.util.ArrayList;

import rx.Observable;

public abstract class Characteristic {
    protected long lastTimestamp;
    protected int lastSequence;
    private String id;
    protected double frequency;
    private String name;

    public Characteristic(String id, String name, double frequency) {
        this.id = id;
        this.frequency = frequency;
        this.name=name;
    }

    public double getFrequency() {
        return frequency;
    }

    public String getName() {
        return name;
    }

    abstract public Observable<Data> getObservable(RxBleConnection rxBleConnection, ArrayList<Sensor> sensors);

    public String getId() {
        return id;
    }

    protected long correctTimeStamp(int curSequence, int maxLimit) {
        long time;
        long curTime = DateTime.getDateTime();
        int diff = (curSequence - lastSequence + maxLimit) % maxLimit;
        time = (long) (lastTimestamp + (1000.0*diff)/frequency);
        if (curTime < time || curTime - time > 5000)
            time = curTime;
        return time;
    }

}
